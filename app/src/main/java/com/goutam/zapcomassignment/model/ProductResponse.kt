package com.goutam.zapcomassignment.model


data class ProductDetails(
    val sectionType: String,
    val items: List<Product>
)

data class Product(
    val title: String,
    val image: String
)
