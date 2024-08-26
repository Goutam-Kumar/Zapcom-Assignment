package com.goutam.zapcomassignment.interceptor

import android.content.Context
import com.goutam.zapcomassignment.utils.NoInternetException
import com.goutam.zapcomassignment.utils.ViewUtils
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    private val context: Context
): Interceptor {
    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!ViewUtils.isInternetAvailable(applicationContext))
            throw NoInternetException("Check if you have internet/Wi-Fi connectivity available")
        return chain.proceed(chain.request())
    }
}