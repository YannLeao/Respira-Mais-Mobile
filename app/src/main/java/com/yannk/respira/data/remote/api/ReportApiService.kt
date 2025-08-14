package com.yannk.respira.data.remote.api

import com.yannk.respira.data.remote.model.response.DailyReportsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ReportApiService {

    @GET("/relatorios/resumo_diario")
    suspend fun getDailySummary(
        @Header("Authorization") token: String,
        @Query("data") date: String
    ): Response<DailyReportsResponse>

    @GET("/relatorios/por_periodo")
    suspend fun getRangeReports(
        @Header("Authorization") token: String,
        @Query("data_inicio") startDate: String,
        @Query("data_fim") endDate: String
    ): Response<List<DailyReportsResponse>>
}
