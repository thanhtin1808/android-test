package com.example.cubandroidtest.repository

import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.data.remote.ApiService
import com.example.cubandroidtest.ui.common.BaseResult
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : NewsRepository {

    override suspend fun getNewsList(
        keyword: String,
        language: String,
        pageSize: Int,
        page: Int
    ): BaseResult<List<NewsArticle>> = try {
//        val response = apiService.getNewsList(
//            keyword = keyword,
//            language = language,
//            pageSize = pageSize,
//            page = page,
//        )
//
//        if (response.isSuccess) {
            BaseResult.Success(getFallbackNews())
//        } else {
//            BaseResult.Error(throwable = Exception("Unknown error"),
//                responseBody = Gson().toJson(response))
//        }

    } catch (e: HttpException) {
        BaseResult.Error(e, e.response()?.errorBody()?.string())
    } catch (e: Exception) {
        BaseResult.Error(e)
    }
}

private fun getFallbackNews() = listOf(
    NewsArticle(
        title = "Spyware Maker NSO Group is Paving a Path Back into Trump's America",
        urlToImage = "https://media.wired.com/photos/681e91cbb3a1c53a160e45a8/191:100/w_1280,c_limit/Your-Gear-News-of-the-Week_052025_Lede.jpg",
        description = "The Global Award for Sustainable Architecture, created in 2006 by architect and scholar Jana Revedin, annually recognizes five architects—or offices—from around the world whose practices are grounded in the principles of sustainable development, participatory…",
        content = "© Bicubik Photography\\r\\nThe Global Award for Sustainable Architecture, created in 2006 by architect and scholar Jana Revedin, annually recognizes five architectsor officesfrom around the world whose p… [+6977 chars]",
        author = "Peter Wade",
        publishedAt = "2025-05-11T20:33:21Z",
        url = "https://www.wired.com/story/samsung-galaxy-s25-edge-reveal-confirmed-and-lenovo-launches-a-new-3d-laptop-your-gear-news-of-the-week/"
    ),
    NewsArticle(
        title = "Spyware Maker NSO Group is Paving a Path Back into Trump's America",
        urlToImage = "",
        content = "The Israeli spyware maker, still on the US Commerce Department's bla...",
    ),
    NewsArticle(
        title = "Spyware Maker NSO Group is Paving a Path Back into Trump's America",
        urlToImage = "https://blogger.googleusercontent.com/img/a/AVvXsEj_CyaABeyGjA0Ll_8pZtRLfDgAp-WXQ_Ds-AMmavEo0GqpCzF1LlqyvutvjapUNIVeCL7WY2f8eXU67JktzZ5jecdY14eWUvMXfYCTQdwHU8Pl-DFb41HL1nrVr8YCsh6UYjSY6TJH7jXLdoGQ2QdE4ZY734fzyJzrfWEI1pSc81Qv0OpdITrVRpEgYJU=w640-h358",
        content = "The Israeli spyware maker, still on the US Commerce Department's bla...",
    ),
)
