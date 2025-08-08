package com.yannk.respira.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
data class SessionEntity(
    @PrimaryKey val sessionId: Int,
    val ambiente: String,
    val quantidadeTosse: Int,
    val quantidadeEspirro: Int,
    val outrosEventos: Int,
    val dataHoraInicio: String,
    val dataHoraFim: String
)

