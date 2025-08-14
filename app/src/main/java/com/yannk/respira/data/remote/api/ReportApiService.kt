package com.yannk.respira.data.remote.api

import com.yannk.respira.data.local.model.DailySummary
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReportApiService {

    @GET("/relatorios/resumo_diario")
    suspend fun getDailySummary(
        @Query("data") date: String
    ): Response<DailySummary>
}
