package com.goutam.zapcomassignment.executor

import retrofit2.Response

object ApiExecutor {
    suspend fun <T> triggerApiCall( apiCall: suspend () -> Response<T> ): NetworkResponse<T> {
        try{
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResponse.Success(it)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (ex: Exception) {
            return error(ex.message ?: ex.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResponse<T> =
        NetworkResponse.Error("Error: $errorMessage")
}