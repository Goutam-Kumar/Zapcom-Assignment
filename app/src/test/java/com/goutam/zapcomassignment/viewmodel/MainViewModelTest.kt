package com.goutam.zapcomassignment.viewmodel

import app.cash.turbine.test
import com.goutam.zapcomassignment.executor.NetworkResponse
import com.goutam.zapcomassignment.model.Product
import com.goutam.zapcomassignment.model.ProductDetails
import com.goutam.zapcomassignment.repository.ProductsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private lateinit var productRepo: ProductsRepository
    private lateinit var mainViewModel: MainViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        productRepo = mockk(relaxed = true)
        mainViewModel = MainViewModel(productRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchProducts success updates productDetailsList and isLoading`() = runTest {
        val productDetails = listOf(ProductDetails("header", listOf(Product(title = "mock-title", image = "mock-image-path"))))
        val successResponse = NetworkResponse.Success(productDetails)
        coEvery { productRepo.fetchProductDetails() } returns flowOf(successResponse)
        mainViewModel.fetchProducts()

        mainViewModel.productDetailsList.test {
            assertEquals(productDetails, awaitItem())
            cancelAndConsumeRemainingEvents()
        }

        mainViewModel.isLoading.test {
            assertEquals(false, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `fetchProducts error updates errorMessage and isLoading`() = runTest {
        val errorMsg = "Error observed in fetchProducts api call"
        val errorResponse = NetworkResponse.Error(errorMsg)
        coEvery { productRepo.fetchProductDetails() } returns flowOf(errorResponse)
        mainViewModel.fetchProducts()
        mainViewModel.errorMessage.test {
            assertEquals(errorMsg, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        mainViewModel.isLoading.test {
            assertEquals(false, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test dismissProgressDialog while invoked`() = runTest {
        mainViewModel.dismissProgressDialog()
        mainViewModel.isLoading.test {
            assertEquals(false, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}