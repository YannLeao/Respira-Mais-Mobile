package com.yannk.respira.data.repository

import com.yannk.respira.data.local.UserPreferences
import com.yannk.respira.data.local.dao.UserDao
import com.yannk.respira.data.local.model.User
import com.yannk.respira.data.remote.api.ApiClient
import com.yannk.respira.data.remote.model.LoginDto
import com.yannk.respira.data.remote.model.UserDto
import com.yannk.respira.util.ResultState
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val api: ApiClient,
    private val preferences: UserPreferences
) {
    suspend fun register(dto: UserDto): ResultState<String> {
        return try {
            val response = api.apiService.register(dto)
            preferences.saveToken(response.access_token)

            val user = User(
                id = response.user.id,
                name = response.user.name,
                email = response.user.email
            )
            userDao.insert(user)

            ResultState.Success("Cadastro realizado com sucesso")
        } catch (e: HttpException) {
            val errorMsg = when (e.code()) {
                400 -> "Usuário já existe"
                500 -> "Erro no servidor"
                else -> "Erro inesperado"
            }
            ResultState.Error(errorMsg)
        } catch (e: IOException) {
            ResultState.Error("Erro de conexão. Verifique sua internet.")
        } catch (e: Exception) {
            ResultState.Error("Erro desconhecido: ${e.localizedMessage}")
        }
    }

    suspend fun login(loginDto: LoginDto): ResultState<String> {
        return try {
            val response = api.apiService.login(loginDto)
            preferences.saveToken(response.access_token)

            val user = User(
                id = response.user.id,
                email = response.user.email,
                name = response.user.name
            )
            userDao.insert(user)
            ResultState.Success("Login bem-sucedido")

        } catch (e: HttpException) {
            val msg = when (e.code()) {
                400 -> "Credenciais inválidas"
                500 -> "Erro no servidor"
                else -> "Erro inesperado"
            }
            ResultState.Error(msg)
        } catch (e: IOException) {
            ResultState.Error("Sem conexão com a internet.")
        } catch (e: Exception) {
            ResultState.Error("Erro: ${e.localizedMessage}")
        }
    }

    suspend fun logout() {
        userDao.clear()
        preferences.clear()
    }

    suspend fun getToken(): String? = preferences.getToken()

    suspend fun getUserName(): String {
        return userDao.getUserName()
    }

    suspend fun getUserEmail(): String {
        return userDao.getUserEmail()
    }
}