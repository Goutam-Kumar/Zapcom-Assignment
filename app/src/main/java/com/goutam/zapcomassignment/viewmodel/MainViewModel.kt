package com.goutam.zapcomassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goutam.zapcomassignment.executor.NetworkResponse
import com.goutam.zapcomassignment.model.ProductDetails
import com.goutam.zapcomassignment.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val productRepo: ProductsRepository): ViewModel() {
    private val _productDetailsList = MutableStateFlow<List<ProductDetails>>(emptyList())
    val productDetailsList: StateFlow<List<ProductDetails>> = _productDetailsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun fetchProducts() = viewModelScope.launch {
        _isLoading.value = true
        productRepo.fetchProductDetails().collect { result ->
            _isLoading.value = false
            when(result) {
                is NetworkResponse.Success -> { _productDetailsList.value = result.data ?: emptyList() }
                is NetworkResponse.Error -> { _errorMessage.value = result.message.orEmpty() }
            }
        }
    }

    fun dismissProgressDialog() {
        _isLoading.value = false
    }
}