package com.yannk.respira.service

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import com.yannk.respira.data.remote.api.ApiClient
import com.yannk.respira.service.utils.NotificationUtil
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

    private var token: String? = null
    private var sessionId: Int? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(NotificationUtil.NOTIFICATION_ID, NotificationUtil.createNotification(this))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        token = intent?.getStringExtra("token")
        sessionId = intent?.getIntExtra("session_id", -1)

        if (!isRunning && token != null && sessionId != null && sessionId != -1) {
            isRunning = true
            coroutineScope.launch {
                while (isRunning) {
                    val file = gravarWav(applicationContext)
                    enviarAudio(file, sessionId!!)
                    delay(10000)
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

    private fun enviarAudio(file: File, sessionId: Int) {
        val tokenHeader = "Bearer $token"
        val requestFile = file.asRequestBody("audio/wav".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        coroutineScope.launch {
            try {
                val response = apiClient.apiService.monitorarAudio(
                    token = tokenHeader,
                    file = filePart,
                    sessionId = sessionId)
                Log.d("SleepService", "Áudio enviado: ${response.isSuccessful}")
            } catch (e: Exception) {
                Log.e("SleepService", "Erro ao enviar áudio", e)
            }
        }
    }
}
