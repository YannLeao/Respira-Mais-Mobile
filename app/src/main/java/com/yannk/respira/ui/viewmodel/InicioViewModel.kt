package com.yannk.respira.ui.viewmodel

import android.content.Context
import android.media.MediaRecorder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yannk.respira.data.remote.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class InicioViewModel @Inject constructor(
    private val apiClient: ApiClient
): ViewModel() {

    private var recorder: MediaRecorder? = null
    private var _filePath: String? = null

    fun startRecording(context: Context) {
        val audioFile = File.createTempFile("gravacao", ".wav", context.cacheDir)
        _filePath = audioFile.absolutePath

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(_filePath)
            prepare()
            start()
        }
    }

    fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    fun enviarAudio(callback: (Boolean) -> Unit) {
        val path = _filePath ?: return
        val file = File(path)
        val requestFile = file.asRequestBody("audio/wav".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        viewModelScope.launch {
            try {
                val response = apiClient.apiService.analisar_audio(body)
                callback(response.isSuccessful)
            } catch (e: Exception) {
                callback(false)
            }
        }
    }
}