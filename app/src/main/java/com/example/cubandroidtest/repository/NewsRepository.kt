package com.example.cubandroidtest.repository

import com.example.cubandroidtest.data.model.NewsArticle

interface NewsRepository {
    suspend fun getNewsList(
        language: String = "en",
        country: String? = "us",
        pageSize: Int = 20,
        page: Int = 1
    ): List<NewsArticle>
}
