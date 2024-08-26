package com.goutam.zapcomassignment.service

import com.goutam.zapcomassignment.model.ProductDetails
import retrofit2.Response
import retrofit2.http.GET

interface AppService {

    @GET("b/5BEJ")
    suspend fun getProductList(): Response<List<ProductDetails>>
}