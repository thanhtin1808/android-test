package com.example.cubandroidtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.repository.NewsRepository
import com.example.cubandroidtest.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository,
) : BaseViewModel() {

    private val _newsList = MutableLiveData<List<NewsArticle>>()
    val newsList: LiveData<List<NewsArticle>> = _newsList
    private val defaultKeyWords = "news"

    val searchQuery = MutableStateFlow("")

    init {
        fetchNews()
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun observeSearchAndLanguage(languageFlow: Flow<String>) {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .combine(languageFlow) { query, language ->
                    query to language
                }
                .collectLatest { (query, language) ->
                    fetchNews(language = language, keyword = query)
                }
        }
    }

    fun fetchNews(language: String = "en", keyword: String? = null) {
        val finalKeyword = if (keyword.isNullOrBlank()) defaultKeyWords else keyword

        launchSafe {
            val result = repository.getNewsList(keyword = finalKeyword, language = language)
            handleResult(
                result,
                onSuccess = { articles ->
                    _newsList.value = articles
                },
                onError = {
                    _newsList.value = emptyList()
                })
        }
    }
}