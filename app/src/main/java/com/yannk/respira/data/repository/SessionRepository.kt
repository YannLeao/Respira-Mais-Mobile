package com.yannk.respira.data.repository

import com.yannk.respira.data.local.dao.SessionDao
import com.yannk.respira.data.local.model.SessionEntity
import com.yannk.respira.data.remote.client.ApiClient
import com.yannk.respira.data.remote.model.response.SessionReportResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class SessionRepository @Inject constructor(
    private val sessionDao: SessionDao,
    private val apiClient: ApiClient
) {

    suspend fun getLatestSession(): SessionEntity? {
        return sessionDao.getLatestSession()
    }

    suspend fun iniciarSessao(token: String): Int {
        val response = apiClient.monitoringService.startSession("Bearer $token")
        return response.body()?.session_id ?: throw Exception("Falha ao iniciar sessão")
    }

    suspend fun analisarAmbiente(token: String, sessionId: Int, file: File): String {
        val requestFile = file.asRequestBody("audio/wav".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

        val response = apiClient.monitoringService.analyzeEnvironment(
            "Bearer $token",
            filePart,
            sessionId
        )
        return response.body()?.status ?: "erro"
    }

    suspend fun finalizarSessao(token: String, sessionId: Int): SessionReportResponse {
        val response = apiClient.monitoringService.finishSession("Bearer $token", sessionId)
        return response.body() ?: throw Exception("Erro ao finalizar sessão")
    }

    suspend fun logout() {
        sessionDao.clearAll()
    }

    suspend fun salvarSessao(report: SessionReportResponse) {
        val entity = SessionEntity(
            sessionId = report.session_id,
            ambiente = report.ambiente,
            quantidadeTosse = report.quantidade_tosse,
            quantidadeEspirro = report.quantidade_espirro,
            outrosEventos = report.outros_eventos,
            dataHoraInicio = report.data_hora_inicio,
            dataHoraFim = report.data_hora_fim
        )
        sessionDao.insert(entity)
    }
}
