package com.yannk.respira.data.remote.model.response

data class AuthResponse(
    val accessToken: String,
    val tokenType: String,
    val user: UserResponse
)
