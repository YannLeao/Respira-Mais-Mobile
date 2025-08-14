package com.yannk.respira.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// Cria uma instância do DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class ThemeRepository @Inject constructor(@ApplicationContext private val context: Context) {

    // Chave para salvar a preferência do modo escuro
    private val isDarkModeKey = booleanPreferencesKey("is_dark_mode")

    // Fluxo para ler a preferência do modo escuro
    val isDarkMode: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[isDarkModeKey] ?: false // Retorna false como padrão
        }

    // Função para salvar a preferência
    suspend fun setDarkMode(isDarkMode: Boolean) {
        context.dataStore.edit { settings ->
            settings[isDarkModeKey] = isDarkMode
        }
    }
}