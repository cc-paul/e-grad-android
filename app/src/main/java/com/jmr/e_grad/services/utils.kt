package com.jmr.e_grad.services

import android.app.Dialog
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.R
import com.google.android.material.snackbar.Snackbar

class utils {
    var dialog: Dialog? = null

    fun showSnackMessage(myView : View, message : String,duration: Int) {
        Snackbar.make(myView, message, duration).apply {
            val textView = view.findViewById<TextView>(R.id.snackbar_text)
            textView.isSingleLine = false
            show()
        }.setAction("Close") {

        }
    }

    fun showToastMessage(myContext: Context, message: String) {
        Toast.makeText(myContext,message, Toast.LENGTH_SHORT).show()
    }

    fun showProgress(myContext: Context) {
        var builder = AlertDialog.Builder(myContext)
        builder.setView(com.jmr.e_grad.R.layout.progress)
        dialog = builder.create()
        (dialog as AlertDialog).setCancelable(false)
        (dialog as AlertDialog).show()
    }

    fun closeProgress() {
        dialog?.dismiss()
    }

    fun hasInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun isGPSEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun formatDuration(seconds: Long): String {
        // Average number of seconds in a month (30 days * 24 hours * 60 minutes * 60 seconds)
        val secondsInMonth: Long = 30 * 24 * 60 * 60

        // Calculate months, days, hours, and minutes
        val months = (seconds / secondsInMonth).toInt()
        val remainingSeconds = seconds % secondsInMonth
        val days = (remainingSeconds / (24 * 60 * 60)).toInt()
        val hours = ((remainingSeconds % (24 * 60 * 60)) / (60 * 60)).toInt()
        val minutes = ((remainingSeconds % (60 * 60)) / 60).toInt()

        // Format the duration
        val formattedDuration = "$months months, $days days, $hours hours, $minutes minutes"

        return formattedDuration
    }

}