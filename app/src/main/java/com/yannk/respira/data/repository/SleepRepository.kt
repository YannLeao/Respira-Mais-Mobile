package com.yannk.respira.data.repository

import com.yannk.respira.data.local.model.SessionData
import com.yannk.respira.data.remote.api.ApiClient
import javax.inject.Inject

class SleepRepository @Inject constructor(
    apiClient: ApiClient
) {

    fun getLatestSession(): SessionData {
        TODO()
    }
}