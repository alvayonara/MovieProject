package com.alvayonara.movieproject.core.base

import com.alvayonara.movieproject.core.data.source.remote.network.Result
import retrofit2.Response

abstract class BaseDataSource {

    /**
     * Safe API call
     */
    suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                Result.success(body)
            } else Result.error(errorCode = response.code(), errorBody = response.errorBody())
        } catch (e: Exception) {
            Result.error(message = e.message.orEmpty())
        }
    }
}
