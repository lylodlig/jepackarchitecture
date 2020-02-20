package com.example.jepack_architecture.data.repository

import com.example.jepack_architecture.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    suspend fun loadHomeData(page:Int) =
        withContext(Dispatchers.IO) {
            RetrofitManager.apiService.homeArticles(page)
        }

}