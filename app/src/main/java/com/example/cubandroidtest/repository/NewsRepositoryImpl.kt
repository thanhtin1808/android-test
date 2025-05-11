package com.example.cubandroidtest.repository

import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.data.remote.ApiService
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : NewsRepository {
    override suspend fun getNewsList(
        language: String,
        country: String?,
        pageSize: Int,
        page: Int
    ): List<NewsArticle> {
        val response = apiService.getNewsList(language, country, pageSize, page)
        return if (response.status == "ok") {
            response.articles.orEmpty()
        } else {
            emptyList()
        }
    }
}
