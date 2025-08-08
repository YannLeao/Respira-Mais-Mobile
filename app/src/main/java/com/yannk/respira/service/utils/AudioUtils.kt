package com.yannk.respira.service.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

@Throws(IOException::class, SecurityException::class)
fun gravarWav(context: Context, durationInSeconds: Int = 5): File {
    // Verificação de permissão
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED) {
        throw SecurityException("Permissão RECORD_AUDIO não concedida")
    }

    // Configurações de áudio
    val sampleRate = 44100
    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat) * 2

    // Arquivos temporários
    val pcmFile = File.createTempFile("audio_", ".pcm", context.cacheDir).apply { deleteOnExit() }
    val wavFile = File.createTempFile("audio_", ".wav", context.cacheDir).apply { deleteOnExit() }

    var audioRecord: AudioRecord? = null
    var outputStream: FileOutputStream? = null

    try {
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )

        outputStream = FileOutputStream(pcmFile)

        val buffer = ByteArray(bufferSize)
        audioRecord.startRecording()

        // Gravação com timeout
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < durationInSeconds * 1000) {
            val bytesRead = audioRecord.read(buffer, 0, buffer.size)
            if (bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead)
            }
        }

        // Garantia de que todos os bytes foram escritos
        outputStream.flush()
        outputStream.fd.sync() // Força escrita no disco

        // Conversão para WAV
        convertPcmToWav(pcmFile, wavFile, sampleRate, channelConfig, audioFormat)

        // Verificação final do arquivo
        if (!wavFile.exists() || wavFile.length() == 0L) {
            throw IOException("Arquivo WAV não foi criado corretamente")
        }

        return wavFile

    } finally {
        // Liberação de recursos em ordem segura
        try {
            audioRecord?.stop()
            audioRecord?.release()
        } catch (e: Exception) {
            Log.e("AudioRec", "Erro ao liberar AudioRecord", e)
        }

        try {
            outputStream?.close()
        } catch (e: Exception) {
            Log.e("AudioRec", "Erro ao fechar FileOutputStream", e)
        }

        // Limpeza do arquivo PCM temporário
        try {
            if (pcmFile.exists()) pcmFile.delete()
        } catch (e: Exception) {
            Log.e("AudioRec", "Erro ao deletar arquivo PCM", e)
        }
    }
}

@Throws(IOException::class)
private fun convertPcmToWav(pcmFile: File, wavFile: File, sampleRate: Int, channels: Int, encoding: Int) {
    val pcmData = pcmFile.readBytes()
    val totalDataSize = pcmData.size
    val byteRate = sampleRate * channels * 2 // 16 bits = 2 bytes

    val header = ByteBuffer.allocate(44).apply {
        order(ByteOrder.LITTLE_ENDIAN)
        put("RIFF".toByteArray())                     // ChunkID
        putInt(36 + totalDataSize)                    // ChunkSize
        put("WAVE".toByteArray())                     // Format
        put("fmt ".toByteArray())                     // Subchunk1ID
        putInt(16)                                     // Subchunk1Size (16 for PCM)
        putShort(1.toShort())                         // AudioFormat (1 = PCM)
        putShort(channels.toShort())                   // NumChannels
        putInt(sampleRate)                             // SampleRate
        putInt(byteRate)                               // ByteRate
        putShort((channels * 2).toShort())             // BlockAlign
        putShort(16.toShort())                         // BitsPerSample
        put("data".toByteArray())                      // Subchunk2ID
        putInt(totalDataSize)                          // Subchunk2Size
    }.array()

    FileOutputStream(wavFile).use { output ->
        output.write(header)
        output.write(pcmData)
        output.flush()
        output.fd.sync() // Força escrita no disco
    }
}