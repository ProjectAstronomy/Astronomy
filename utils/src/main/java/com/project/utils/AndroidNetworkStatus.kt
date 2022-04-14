package com.project.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AndroidNetworkStatus(context: Context) {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()
    private val networkState = MutableStateFlow(false)

    init {
        val request = NetworkRequest.Builder().build()

        connectivityManager?.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkState.value = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                networkState.value = false
            }

            override fun onUnavailable() {
                super.onUnavailable()
                networkState.value = false
            }
        })
    }

    fun isNetworkAvailable(): Boolean = networkState.value

    fun isNetworkAvailableInFlow(): StateFlow<Boolean> = networkState
}