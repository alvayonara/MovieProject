package com.alvayonara.movieproject.core.data.source.remote.network

import okhttp3.ResponseBody

data class Result<out T>(
    val status: Status,
    val body: T?,
    val errorCode: Int = 0,
    val errorBody: ResponseBody? = null,
    val message: String = ""
) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        @JvmStatic
        fun <T> success(data: T?): Result<T> = Result(Status.SUCCESS, data)

        @JvmStatic
        @JvmOverloads
        fun <T> error(
            data: T? = null, errorCode: Int = 0,
            errorBody: ResponseBody? = null, message: String = ""
        ): Result<T> = Result(Status.ERROR, data, errorCode, errorBody, message)

        @JvmStatic
        @JvmOverloads
        fun <T> loading(data: T? = null): Result<T> = Result(Status.LOADING, data)
    }
}