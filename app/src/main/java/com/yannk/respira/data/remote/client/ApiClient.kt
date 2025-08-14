package com.yannk.respira.data.remote.client

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.yannk.respira.data.remote.api.AuthApiService
import com.yannk.respira.data.remote.api.MonitoringApiService
import com.yannk.respira.data.remote.api.ReportApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val baseUrl = "http://192.168.18.3:8000/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Configura Gson para converter automaticamente snake_case → camelCase
    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val authService: AuthApiService = retrofit.create(AuthApiService::class.java)
    val monitoringService: MonitoringApiService = retrofit.create(MonitoringApiService::class.java)
    val reportService: ReportApiService = retrofit.create(ReportApiService::class.java)
}