package com.huania.eew_bid.data.repository

import com.huania.eew_bid.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    suspend fun login(username: String, password: String) =
        withContext(Dispatchers.IO) {
            RetrofitManager.apiService.login(username, password)
        }

}