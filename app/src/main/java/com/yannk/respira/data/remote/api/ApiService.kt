package com.yannk.respira.data.remote.api

import com.yannk.respira.data.remote.model.AuthResponse
import com.yannk.respira.data.remote.model.LoginDto
import com.yannk.respira.data.remote.model.UserDto
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/analisar_audio")
    suspend fun analisar_audio(@Part file: MultipartBody.Part): Response<ResponseBody>

    @POST("/register")
    suspend fun register(@Body userDto: UserDto): AuthResponse

    @POST("/login")
    suspend fun login(@Body loginDto: LoginDto): AuthResponse
}