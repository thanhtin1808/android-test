package com.example.cubandroidtest.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cubandroidtest.data.model.remote.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private fun parseErrorMessage(json: String?): String {
        return try {
            val error = Gson().fromJson(json, ErrorResponse::class.java)
            error.message ?: "Unknown error"
        } catch (e: Exception) {
            "Something went wrong"
        }
    }

    protected fun <T> handleResult(
        result: BaseResult<T>,
        onSuccess: (T) -> Unit,
        onError: ((String) -> Unit)? = null,
    ) {
        _loading.postValue(false)
        when (result) {
            is BaseResult.Success -> onSuccess(result.data)
            is BaseResult.Error -> {
                val errorMsg = parseErrorMessage(result.responseBody)
                _errorMessage.postValue(errorMsg)
                onError?.invoke(errorMsg)
            }
        }
    }

    protected fun launchSafe(block: suspend () -> Unit) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                block()
            } catch (e: Exception) {
                _errorMessage.postValue("Unexpected error: ${e.localizedMessage}")
            } finally {
                _loading.postValue(false)
            }
        }
    }
}
