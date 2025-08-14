package com.yannk.respira.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.yannk.respira.data.remote.client.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    apiClient: ApiClient
): ViewModel() {
    val weeklyData = mutableStateOf(
        listOf(
            "Seg" to 150f,
            "Ter" to 280f,
            "Qua" to 90f,
            "Qui" to 210f,
            "Sex" to 180f,
            "Sáb" to 300f,
            "Dom" to 120f
        )
    )
    val monthlyData = null

    fun refreshData() {
        TODO("Not yet implemented")
    }

}