package com.example.cubandroidtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cubandroidtest.data.model.NewsArticle
import com.example.cubandroidtest.repository.NewsRepository
import com.example.cubandroidtest.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirstViewModel @Inject constructor(
    private val repository: NewsRepository
) : BaseViewModel() {

    private val _newsList = MutableLiveData<List<NewsArticle>>()
    val newsList: LiveData<List<NewsArticle>> = _newsList

    init {
        fetchNews()
    }

    private fun fetchNews() {
        launchSafe {
            val response = repository.getNewsList()
                _newsList.value = response.orEmpty()
            }
    }
}
