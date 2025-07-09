package com.yannk.respira.service.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun gravarWav(context: Context, durationInSeconds: Int = 5): File {
    val permissionGranted = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.RECORD_AUDIO
    ) == PackageManager.PERMISSION_GRANTED

    if (!permissionGranted) {
        throw SecurityException("Permissão RECORD_AUDIO não concedida")
    }

    val sampleRate = 44100
    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT

    val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
    val audioRecord = AudioRecord(
        MediaRecorder.AudioSource.MIC,
        sampleRate,
        channelConfig,
        audioFormat,
        bufferSize
    )

    val rawData = ByteArray(bufferSize)
    val pcmFile = File(context.cacheDir, "gravacao_temp.pcm")
    val wavFile = File(context.cacheDir, "gravacao_final.wav")

    val outputStream = FileOutputStream(pcmFile)

    audioRecord.startRecording()

    val startTime = System.currentTimeMillis()
    while ((System.currentTimeMillis() - startTime) < durationInSeconds * 1000) {
        val read = audioRecord.read(rawData, 0, rawData.size)
        if (read > 0) {
            outputStream.write(rawData, 0, read)
        }
    }

    audioRecord.stop()
    audioRecord.release()
    outputStream.close()

    pcmToWav(pcmFile, wavFile, sampleRate, channelConfig, audioFormat)

    pcmFile.delete()

    return wavFile
}

fun pcmToWav(pcmFile: File, wavFile: File, sampleRate: Int, channelConfig: Int, audioFormat: Int) {
    val pcmData = pcmFile.readBytes()
    val numChannels = if (channelConfig == AudioFormat.CHANNEL_IN_MONO) 1 else 2
    val bitsPerSample = if (audioFormat == AudioFormat.ENCODING_PCM_16BIT) 16 else 8

    val totalDataLen = pcmData.size + 36
    val byteRate = sampleRate * numChannels * bitsPerSample / 8

    val header = ByteArray(44)

    // RIFF/WAVE header
    header[0] = 'R'.code.toByte()
    header[1] = 'I'.code.toByte()
    header[2] = 'F'.code.toByte()
    header[3] = 'F'.code.toByte()
    ByteBuffer.wrap(header, 4, 4).order(ByteOrder.LITTLE_ENDIAN).putInt(totalDataLen)
    header[8] = 'W'.code.toByte()
    header[9] = 'A'.code.toByte()
    header[10] = 'V'.code.toByte()
    header[11] = 'E'.code.toByte()
    header[12] = 'f'.code.toByte()
    header[13] = 'm'.code.toByte()
    header[14] = 't'.code.toByte()
    header[15] = ' '.code.toByte()
    ByteBuffer.wrap(header, 16, 4).order(ByteOrder.LITTLE_ENDIAN).putInt(16) // Subchunk1Size
    ByteBuffer.wrap(header, 20, 2).order(ByteOrder.LITTLE_ENDIAN).putShort(1) // AudioFormat (1 = PCM)
    ByteBuffer.wrap(header, 22, 2).order(ByteOrder.LITTLE_ENDIAN).putShort(numChannels.toShort())
    ByteBuffer.wrap(header, 24, 4).order(ByteOrder.LITTLE_ENDIAN).putInt(sampleRate)
    ByteBuffer.wrap(header, 28, 4).order(ByteOrder.LITTLE_ENDIAN).putInt(byteRate)
    ByteBuffer.wrap(header, 32, 2).order(ByteOrder.LITTLE_ENDIAN).putShort((numChannels * bitsPerSample / 8).toShort()) // BlockAlign
    ByteBuffer.wrap(header, 34, 2).order(ByteOrder.LITTLE_ENDIAN).putShort(bitsPerSample.toShort())
    header[36] = 'd'.code.toByte()
    header[37] = 'a'.code.toByte()
    header[38] = 't'.code.toByte()
    header[39] = 'a'.code.toByte()
    ByteBuffer.wrap(header, 40, 4).order(ByteOrder.LITTLE_ENDIAN).putInt(pcmData.size)

    FileOutputStream(wavFile).use {
        it.write(header)
        it.write(pcmData)
    }
}