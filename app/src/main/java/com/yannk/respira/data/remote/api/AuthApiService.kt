package com.yannk.respira.data.remote.api

import com.yannk.respira.data.remote.model.request.LoginDto
import com.yannk.respira.data.remote.model.request.UserDto
import com.yannk.respira.data.remote.model.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("/auth/register")
    suspend fun register(
        @Body userDto: UserDto
    ): AuthResponse

    @POST("/auth/login")
    suspend fun login(
        @Body loginDto: LoginDto
    ): AuthResponse
}