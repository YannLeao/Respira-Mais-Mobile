package com.yannk.respira.data.remote

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("/analisar_audio")
    suspend fun analisar_audio(
        @Part file: MultipartBody.Part
    ): Response<ResponseBody>
}