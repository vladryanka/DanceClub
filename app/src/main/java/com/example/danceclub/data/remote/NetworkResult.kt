package com.example.danceclub.data.remote

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response

sealed class NetworkResult<out T> {
    data class Success<out T : Any>(val code: Int, val data: T) : NetworkResult<T>()
    data class Error(val code: Int, val errorMsg: String?) : NetworkResult<Nothing>()
    data class Exception(val e: Throwable) : NetworkResult<Nothing>()
}

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                NetworkResult.Success(response.code(), body)
            } else {
                NetworkResult.Error(response.code(), "Response body is null")
            }
        } else {
            when (response.code()) {
                400 -> NetworkResult.Error(response.code(), "User-Agent required")
                401 -> NetworkResult.Error(response.code(), "Incorrect username and password")
                409 -> NetworkResult.Error(response.code(), "User with same phone already exists")
                422 -> {
                    Log.d("Doing",response.errorBody()?.string().toString())
                    NetworkResult.Error(response.code(), "Validation Error")
                }
                else -> NetworkResult.Error(response.code(), "Unknown Error")
            }
        }
    } catch (e: HttpException) {
        Log.d("Doing", e.message().toString())
        NetworkResult.Error(e.code(), e.message())
    } catch (e: Throwable) {
        Log.d("Doing",e.toString())
        NetworkResult.Exception(e)
    }
}

