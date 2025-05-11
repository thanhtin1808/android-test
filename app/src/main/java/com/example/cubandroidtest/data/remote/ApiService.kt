package com.example.cubandroidtest.data.remote

import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.data.model.remote.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/everything")
    suspend fun getNewsList(
        @Query("q") keyword: String = "",
        @Query("language") language: String = "en",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): BaseResponse<List<NewsArticle>>
}
