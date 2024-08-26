package com.goutam.zapcomassignment.repository

import com.goutam.zapcomassignment.datasource.RemoteDataSource
import com.goutam.zapcomassignment.executor.NetworkResponse
import com.goutam.zapcomassignment.model.ProductDetails
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ProductsRepositoryTest {
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var productsRepository: ProductsRepository

    @Before
    fun setUp() {
        remoteDataSource = mockk(relaxed = true)
        productsRepository = ProductsRepository(remoteDataSource)
    }

    @Test
    fun `test fetchProductDetails while success response observed`() = runTest {
        val mockProductDetails: ProductDetails = mockk(relaxed = true)
        val mockResponse = Response.success(listOf(mockProductDetails))
        coEvery { remoteDataSource.getProductList() } returns mockResponse
        productsRepository.fetchProductDetails().collect { result ->
            Assert.assertTrue(result is NetworkResponse.Success)
            result as NetworkResponse.Success
            Assert.assertEquals(listOf(mockProductDetails), result.data)
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}