package com.example.cubandroidtest.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    // Loading indicator
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // Error message
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Common CoroutineExceptionHandler
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loading.postValue(false)
        _errorMessage.postValue(throwable.message)
    }

    // Helper function to launch coroutine with error and loading handled
    protected fun launchSafe(block: suspend () -> Unit) {
        _loading.value = true
        CoroutineScope(Dispatchers.Main + exceptionHandler).launch {
            block()
            _loading.value = false
        }
    }
}
