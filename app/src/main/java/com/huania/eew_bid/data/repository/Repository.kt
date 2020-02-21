package com.huania.eew_bid.data.repository

import com.huania.eew_bid.net.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository {
    suspend fun loadHomeData(page:Int) =
        withContext(Dispatchers.IO) {
            RetrofitManager.apiService.homeArticles(page)
        }

}