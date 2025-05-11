package com.example.cubandroidtest.ui.common

sealed class BaseResult<out T> {
    data class Success<T>(val data: T) : BaseResult<T>()
    data class Error(val throwable: Throwable, val responseBody: String? = null) : BaseResult<Nothing>()
}