package com.yannk.respira.ui.viewmodel 

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yannk.respira.data.repository.ThemeRepository // Importe seu repositório
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeRepository: ThemeRepository
) : ViewModel() {

    // Expõe o estado do tema coletado do repositório
    val isDarkMode: StateFlow<Boolean> = themeRepository.isDarkMode
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false // Valor inicial
        )

    // Função para o Switch chamar
    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            themeRepository.setDarkMode(isDarkMode)
        }
    }
}