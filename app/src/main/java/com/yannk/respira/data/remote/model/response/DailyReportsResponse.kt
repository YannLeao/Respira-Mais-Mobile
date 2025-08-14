package com.yannk.respira.data.remote.model.response

data class DailyReportsResponse(
    val data: String,
    val total_sessoes: Int,
    val total_tosse: Int,
    val total_espirro: Int,
    val total_outros_eventos: Int,
    val ambiente_predominante: String,
    val duracao_total_minutos: Int
)
