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

    init {
        fetchNews()
    }

    fun fetchNews(language: String = "en", keyword: String? = null) {
        val finalKeyword = if (keyword.isNullOrBlank()) "news" else keyword

        launchSafe {
            val result = repository.getNewsList(keyword = finalKeyword, language = language)

            handleResult(result, onSuccess = { articles ->
                _newsList.value = articles
            }, onError = {
                _newsList.value = emptyList()
            })
        }
    }
}

