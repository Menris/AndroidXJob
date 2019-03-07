package kz.cheesenology.androidxtest.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService


object NetworkUtil {
    val TYPE_WIFI = 1
    val TYPE_MOBILE = 2
    val TYPE_NOT_CONNECTED = 0
    val NETWORK_STATUS_NOT_CONNECTED = 0
    val NETWORK_STATUS_WIFI = 1
    val NETWORK_STATUS_MOBILE = 2

    private fun getConnectivityStatus(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI

            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE
        }
        return TYPE_NOT_CONNECTED
    }

    fun getConnectivityStatusString(context: Context): Int {
        val conn = NetworkUtil.getConnectivityStatus(context)
        var status = 0
        when (conn) {
            NetworkUtil.TYPE_WIFI -> status = NETWORK_STATUS_WIFI
            NetworkUtil.TYPE_MOBILE -> status = NETWORK_STATUS_MOBILE
            NetworkUtil.TYPE_NOT_CONNECTED -> status = NETWORK_STATUS_NOT_CONNECTED
        }
        return status
    }
}