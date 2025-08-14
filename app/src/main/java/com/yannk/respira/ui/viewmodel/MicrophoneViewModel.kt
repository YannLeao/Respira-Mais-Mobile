package com.yannk.respira.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yannk.respira.data.remote.client.ApiClient
import com.yannk.respira.service.utils.gravarWav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MicrophoneViewModel @Inject constructor(
    private val apiClient: ApiClient
) : ViewModel() {

    private var _filePath: String? = null
    private var isRecording = false

    fun startRecording(context: Context, durationInSeconds: Int = 5) {
        if (isRecording) return

        isRecording = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val wavFile = gravarWav(context, durationInSeconds)
                _filePath = wavFile.absolutePath
            } catch (e: SecurityException) {
                Log.e("MicrophoneViewModel", "Permissão negada: ${e.message}")
            } finally {
                isRecording = false
            }
        }
    }

    fun enviarAudio(callback: (Boolean) -> Unit) {
        val path = _filePath ?: return
        val file = File(path)
        val requestFile = file.asRequestBody("audio/wav".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        viewModelScope.launch {
            try {
//                val response = apiClient.apiService.analisarAudio(body)
//                callback(response.isSuccessful)
            } catch (e: Exception) {
                callback(false)
            }
        }
    }

}