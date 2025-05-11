package com.example.cubandroidtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cubandroidtest.data.model.NewsArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {

    private val _selectedArticle = MutableLiveData<NewsArticle>()
    val selectedArticle: LiveData<NewsArticle> get() = _selectedArticle

    fun selectArticle(article: NewsArticle) {
        _selectedArticle.value = article
    }
}