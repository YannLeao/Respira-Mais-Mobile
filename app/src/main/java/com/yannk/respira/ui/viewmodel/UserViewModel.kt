package com.yannk.respira.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yannk.respira.data.remote.model.LoginDto
import com.yannk.respira.data.remote.model.UserDto
import com.yannk.respira.data.repository.UserRepository
import com.yannk.respira.util.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel()  {

    private val _registerState = MutableStateFlow<ResultState<String>?>(null)
    val registerState: StateFlow<ResultState<String>?> = _registerState

    private val _loginState = MutableStateFlow<ResultState<String>?>(null)
    val loginState: StateFlow<ResultState<String>?> = _loginState

    fun register(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _registerState.value = ResultState.Error("Preencha todos os campos.")
            return
        }
        viewModelScope.launch {
            val userDto = UserDto(name, email, password)
            _registerState.value = ResultState.Loading
            _registerState.value = repository.register(userDto)
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _registerState.value = ResultState.Error("Preencha todos os campos.")
            return
        }
        viewModelScope.launch {
            val loginDto = LoginDto(email, password)
            _loginState.value = ResultState.Loading
            _loginState.value = repository.login(loginDto)
        }
    }

    fun clearRegisterState() {
        _registerState.value = null
    }

    fun clearLoginState() {
        _registerState.value = null
    }
}