package com.goutam.zapcomassignment.executor

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

class ApiExecutorTest {


    @Test
    fun `test triggerApiCall with a success Response`() = runTest {
        val mockResponse = mockk<Response<String>> {
            coEvery { isSuccessful } returns true
            coEvery { body() } returns "Success"
        }
        val apiCall: suspend () -> Response<String> = { mockResponse }
        val result = ApiExecutor.triggerApiCall(apiCall)
        assertEquals(NetworkResponse.Success("Success"), result)
    }

    @Test
    fun `test triggerApiCall with a network error`() = runTest {
        val mockResponse = mockk<Response<String>> {
            coEvery { isSuccessful } returns false
            coEvery { code() } returns 404
            coEvery { message() } returns "Not Found"
        }
        val apiCall: suspend () -> Response<String> = { mockResponse }
        val result = ApiExecutor.triggerApiCall(apiCall)
        assertEquals(NetworkResponse.Error("Error: 404 Not Found"), result)
    }

    @Test
    fun `test triggerApiCall with Exception`() = runTest {
        val apiCall: suspend () -> Response<String> = { throw Exception("Network Error") }
        val result = ApiExecutor.triggerApiCall(apiCall)
        assertEquals(NetworkResponse.Error("Error: Network Error"), result)
    }
}