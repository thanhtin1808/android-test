package com.example.cubandroidtest.data.remote

import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.data.model.remote.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
    suspend fun getNewsList(
        @Query("language") language: String = "en",
        @Query("country") country: String? = "us",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): BaseResponse<List<NewsArticle>>
}
