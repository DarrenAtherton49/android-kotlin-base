package com.atherton.sample.util.network.manager

import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Singleton

@Singleton
class AndroidNetworkManager(private val connectivityManager: ConnectivityManager) : NetworkManager {

    override fun isOnline(): Boolean {
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
