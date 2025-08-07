package com.yannk.respira.data.remote.api

import com.yannk.respira.data.remote.model.response.AuthResponse
import com.yannk.respira.data.remote.model.request.LoginDto
import com.yannk.respira.data.remote.model.request.ResumoRequestDto
import com.yannk.respira.data.remote.model.request.UserDto
import com.yannk.respira.data.remote.model.response.AmbienteResponseDto
import com.yannk.respira.data.remote.model.response.ResumoResponseDto
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

interface ApiService {

    @POST("/register")
    suspend fun register(@Body userDto: UserDto): AuthResponse

    @POST("/login")
    suspend fun login(@Body loginDto: LoginDto): AuthResponse

    @Multipart
    @POST("/analisar_audio")
    suspend fun analisarAudio(
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>

    @Multipart
    @POST("/analisar_ambiente")
    suspend fun analisarAmbiente(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<AmbienteResponseDto>

    @Multipart
    @POST("/monitoramento/audio")
    suspend fun enviarMonitoramentoAudio(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("ambiente") ambiente: RequestBody
    ): Response<ResponseBody>

    @POST("/monitoramento/resumo")
    suspend fun obterResumo(
        @Header("Authorization") token: String,
        @Body resumoRequestDto: ResumoRequestDto
    ): Response<ResumoResponseDto>
}