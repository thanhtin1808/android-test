package com.example.cubandroidtest.data.model.remote

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("totalResults")
    val totalResults: Int? = null,
    @SerializedName("articles")
    val articles: T? = null
) {
    val isSuccess: Boolean
        get() = status == "ok"
}