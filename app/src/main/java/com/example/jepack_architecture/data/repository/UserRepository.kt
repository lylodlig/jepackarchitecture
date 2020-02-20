package com.example.jepack_architecture.data.repository

import com.example.jepack_architecture.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    suspend fun login(username: String, password: String) =
        withContext(Dispatchers.IO) {
            RetrofitManager.apiService.login(username, password)
        }

}