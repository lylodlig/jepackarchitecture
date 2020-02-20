package com.example.jepack_architecture.ui.list

import com.example.jepack_architecture.data.db.HomeArticleDetail


data class HomeArticleEntity(
    val `data`: ArticleData,
    val errorCode: Int,
    val errorMsg: String
)

data class ArticleData(
    val curPage: Int,
    val datas: List<HomeArticleDetail>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)