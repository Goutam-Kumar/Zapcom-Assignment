package com.goutam.zapcomassignment.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewUtils {
    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            var result = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            if (!activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
                return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
            return result
        }

        inline fun <V: ViewModel> getViewModelFactory(crossinline getVMObject: () -> V): ViewModelProvider.Factory {
            return object: ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return getVMObject() as T
                }
            }
        }
    }
}