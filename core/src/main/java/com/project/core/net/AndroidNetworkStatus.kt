package com.project.core.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AndroidNetworkStatus(context: Context) {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()
    private val _networkState = MutableStateFlow(true)
    val networkState: StateFlow<Boolean> = _networkState

    init {
        val request = NetworkRequest.Builder().build()

        connectivityManager?.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _networkState.value = true
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkState.value = false
            }

            override fun onUnavailable() {
                super.onUnavailable()
                _networkState.value = false
            }
        })
    }

    fun isNetworkAvailable(): Boolean = networkState.value
}