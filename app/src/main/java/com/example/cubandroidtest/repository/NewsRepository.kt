package com.example.cubandroidtest.repository

import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.ui.common.BaseResult

interface NewsRepository {
    suspend fun getNewsList(
        keyword: String = "",
        language: String = "en",
        pageSize: Int = 20,
        page: Int = 1
    ): BaseResult<List<NewsArticle>>
}
