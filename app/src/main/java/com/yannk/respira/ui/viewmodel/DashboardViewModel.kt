package com.yannk.respira.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.yannk.respira.data.remote.api.ApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor (
    apiClient: ApiClient
): ViewModel() {
    fun refreshData() {
        TODO("Not yet implemented")
    }
}