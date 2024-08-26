package com.goutam.zapcomassignment.executor

sealed class NetworkResponse<out R>{
    data class Success<T>(val data: T): NetworkResponse<T>()
    data class Error(val message: String): NetworkResponse<Nothing>()
}