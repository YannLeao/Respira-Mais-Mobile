package com.yannk.respira.service

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import com.yannk.respira.data.remote.ApiClient
import com.yannk.respira.service.utils.NotificationHelper
import com.yannk.respira.service.utils.gravarWav
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class SleepMonitoringService : LifecycleService() {

    @Inject
    lateinit var apiClient: ApiClient

    private var isRunning = false
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startForeground(NotificationHelper.NOTIFICATION_ID, NotificationHelper.createNotification(this))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (!isRunning) {
            isRunning = true
            coroutineScope.launch {
                while (isRunning) {
                    val file = gravarWav(applicationContext)
                    enviarAudio(file)
                    delay(10000) // espera 10 segundos antes da próxima gravação
                }
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        coroutineScope.cancel()
    }

    private fun enviarAudio(file: File) {
        val requestFile = file.asRequestBody("audio/wav".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        coroutineScope.launch {
            try {
                val response = apiClient.apiService.analisar_audio(body)
                Log.d("SleepService", "Áudio enviado: ${response.isSuccessful}")
            } catch (e: Exception) {
                Log.e("SleepService", "Erro ao enviar áudio", e)
            }
        }
    }
}
