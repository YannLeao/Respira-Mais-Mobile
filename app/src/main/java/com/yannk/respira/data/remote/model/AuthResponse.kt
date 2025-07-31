package com.yannk.respira.data.remote.model

data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val user: UserResponse
)
