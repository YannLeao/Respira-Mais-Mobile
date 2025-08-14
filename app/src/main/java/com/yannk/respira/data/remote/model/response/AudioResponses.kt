package com.yannk.respira.data.remote.model.response

data class SessionStartResponse(
    val sessionId: Int,
    val ambiente: String,
    val dataHoraInicio: String
)

data class MonitoringResponse(
    val status: String,
    val detalhes: String
)

data class SessionReportResponse(
    val sessionId: Int,
    val ambiente: String,
    val quantidadeTosse: Int,
    val quantidadeEspirro: Int,
    val outrosEventos: Int,
    val dataHoraInicio: String,
    val dataHoraFim: String
)