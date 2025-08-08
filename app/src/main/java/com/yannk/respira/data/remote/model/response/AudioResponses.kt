package com.yannk.respira.data.remote.model.response

import com.yannk.respira.data.local.model.SessionEntity

data class SessionStartResponse(
    val session_id: Int,
    val ambiente: String,
    val data_hora_inicio: String
)

data class MonitoringResponse(
    val status: String,
    val detalhes: String
)

data class SessionReportResponse(
    val session_id: Int,
    val ambiente: String,
    val quantidade_tosse: Int,
    val quantidade_espirro: Int,
    val outros_eventos: Int,
    val data_hora_inicio: String,
    val data_hora_fim: String
)