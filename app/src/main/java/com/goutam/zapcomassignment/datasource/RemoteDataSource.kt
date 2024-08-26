package com.goutam.zapcomassignment.datasource

import com.goutam.zapcomassignment.model.ProductDetails
import com.goutam.zapcomassignment.service.AppService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor( private val appService: AppService ) {

    suspend fun getProductList(): Response<List<ProductDetails>> =
        appService.getProductList()
}