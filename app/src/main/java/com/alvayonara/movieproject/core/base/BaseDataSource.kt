package com.alvayonara.movieproject.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.alvayonara.movieproject.core.data.source.remote.network.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

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
            when (e) {
                is IOException -> Result.error(message = e.message.orEmpty())
                else -> Result.error(message = e.message.orEmpty())
            }
        }
    }
}

/**
 * Coroutines LiveData
 */
fun <T> resultLiveData(scope: CoroutineScope, call: suspend () -> Result<T>): LiveData<Result<T>> {
    return liveData(scope.coroutineContext) {
        emit(Result.loading(null))
        withContext(Dispatchers.IO) {
            emit(call.invoke())
        }
    }
}
