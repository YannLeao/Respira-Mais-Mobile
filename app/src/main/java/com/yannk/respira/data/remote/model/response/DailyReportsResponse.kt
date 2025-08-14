package com.yannk.respira.data.remote.model.response

data class DailyReportsResponse(
    val data: String,
    val totalSessoes: Int,
    val totalTosse: Int,
    val totalEspirro: Int,
    val totalOutrosEventos: Int,
    val ambientePredominante: String,
    val duracaoTotalMinutos: Int
)
