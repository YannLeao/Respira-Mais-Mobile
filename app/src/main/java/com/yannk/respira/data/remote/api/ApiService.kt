package com.yannk.respira.data.remote.api

import com.yannk.respira.data.remote.model.request.LoginDto
import com.yannk.respira.data.remote.model.request.UserDto
import com.yannk.respira.data.remote.model.response.AuthResponse
import com.yannk.respira.data.remote.model.response.MonitoringResponse
import com.yannk.respira.data.remote.model.response.SessionReportResponse
import com.yannk.respira.data.remote.model.response.SessionStartResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("/auth/register")
    suspend fun register(
        @Body userDto: UserDto
    ): AuthResponse

    @POST("/auth/login")
    suspend fun login(
        @Body loginDto: LoginDto
    ): AuthResponse

    @POST("/monitoramento/iniciar_sessao")
    suspend fun iniciarSessao(
        @Header("Authorization") token: String
    ): Response<SessionStartResponse>

    @Multipart
    @POST("/monitoramento/analisar_ambiente")
    suspend fun analisarAmbiente(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("session_id") sessionId: RequestBody
    ): Response<MonitoringResponse>

    @Multipart
    @POST("/monitoramento/audio")
    suspend fun monitorarAudio(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("session_id") sessionId: RequestBody
    ): Response<MonitoringResponse>

    @GET("/monitoramento/finalizar/{session_id}")
    suspend fun finalizarSessao(
        @Header("Authorization") token: String,
        @Path("session_id") sessionId: Int
    ): Response<SessionReportResponse>

    @Multipart
    @POST("/analisar_audio")
    suspend fun analisarAudio(
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>

}