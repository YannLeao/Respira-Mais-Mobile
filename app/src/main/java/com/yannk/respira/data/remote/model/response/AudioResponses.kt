package com.yannk.respira.data.remote.model.response

data class AmbienteResponseDto(
    val ambiente: String
)

data class ResumoResponseDto(
    val duracao: Long,
    val eventos: EventosResponse,
    val ambiente: String,
    val classificacao: String
)

data class EventosResponse(
    val tosse: Int,
    val espirro: Int,
    val outros: Int
)