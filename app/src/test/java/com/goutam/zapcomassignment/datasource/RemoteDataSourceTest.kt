package com.goutam.zapcomassignment.datasource

import com.goutam.zapcomassignment.service.AppService
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemoteDataSourceTest {
    private lateinit var appService: AppService
    private lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        appService = mockk(relaxed = true)
        remoteDataSource = RemoteDataSource(appService)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `test getProductList with a mock success response`() = runTest {
        coEvery { appService.getProductList() } returns Response.success(
            listOf(mockk(relaxed = true))
        )
        val response = remoteDataSource.getProductList()
        assertTrue(response.isSuccessful)
        assertEquals(1, (response.body() as List).size)
    }
}