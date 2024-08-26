package com.goutam.zapcomassignment.repository

import com.goutam.zapcomassignment.datasource.RemoteDataSource
import com.goutam.zapcomassignment.executor.ApiExecutor.triggerApiCall
import com.goutam.zapcomassignment.executor.NetworkResponse
import com.goutam.zapcomassignment.model.ProductDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
){
    fun fetchProductDetails(): Flow<NetworkResponse<List<ProductDetails>>> {
        return flow { emit( triggerApiCall { remoteDataSource.getProductList() }) }.flowOn(Dispatchers.IO)
    }
}