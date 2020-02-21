package com.huania.eew_bid.net

import com.huania.eew_bid.data.entity.WanUserEntity
import com.huania.eew_bid.ui.list.HomeArticleEntity
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // 登录
    @POST("/user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username: String, @Field("password") password: String): Response<WanUserEntity>

    // 首页文章
    @GET("/article/list/{page}/json")
    suspend fun homeArticles(@Path("page") page: Int): HomeArticleEntity
}