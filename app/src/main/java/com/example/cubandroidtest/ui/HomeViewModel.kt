package com.example.cubandroidtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.repository.NewsRepository
import com.example.cubandroidtest.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository,
) : BaseViewModel() {

    private val _newsList = MutableLiveData<List<NewsArticle>>()
    val newsList: LiveData<List<NewsArticle>> = _newsList


    fun fetchNews() {
        launchSafe {
            val result = repository.getNewsList()

            handleResult(result, onSuccess = { articles ->
                _newsList.value = articles
            }, onError = {
                _newsList.value = getFallbackNews()
            })
        }
    }

    private fun getFallbackNews() = listOf(
        NewsArticle(
            title = "Spyware Maker NSO Group is Paving a Path Back into Trump's America",
            urlToImage = "https://biztoc.com/cdn/931/og.png",
            content = "The Israeli spyware maker, still on the US Commerce Department's bla...",
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
}

