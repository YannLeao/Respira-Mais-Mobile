package com.yannk.respira.data.remote.api

import com.yannk.respira.data.remote.model.response.MonitoringResponse
import com.yannk.respira.data.remote.model.response.SessionReportResponse
import com.yannk.respira.data.remote.model.response.SessionStartResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MonitoringApiService {

    @POST("/monitoramento/iniciar_sessao")
    suspend fun startSession(
        @Header("Authorization") token: String
    ): Response<SessionStartResponse>

    @Multipart
    @POST("/monitoramento/analisar_ambiente")
    suspend fun analyzeEnvironment(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Query("session_id") sessionId: Int
    ): Response<MonitoringResponse>

    @Multipart
    @POST("/monitoramento/audio")
    suspend fun uploadAudio(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Query("session_id") sessionId: Int
    ): Response<MonitoringResponse>

    @GET("/monitoramento/finalizar/{session_id}")
    suspend fun finishSession(
        @Header("Authorization") token: String,
        @Path("session_id") sessionId: Int
    ): Response<SessionReportResponse>
}
