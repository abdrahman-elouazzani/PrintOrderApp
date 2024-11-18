package com.example.printorderapp.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavDeepLinkBuilder
import com.example.printorderapp.R

class NotificationViewModel(application: Application) : AndroidViewModel(application)  {

    private val channelId = "order_channel"
    private val notificationId = 1
    private val orderId = "12345"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Order Channel"
            val descriptionText = "Notification for Order"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getApplication<Application>().getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    fun sendOrderNotification() {
        val context = getApplication<Application>()

        // Create an Intent using NavDeepLinkBuilder for the OrderDetailFragment deep link
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.navigation)
            .setDestination(R.id.orderDetailsFragment)
            .setArguments(Bundle().apply {
                putString("orderId", orderId)
            })
            .createPendingIntent()

        // Build the notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your app's icon
            .setContentTitle("Order Created")
            .setContentText("Tap to view order details.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Dismiss notification on click

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification.build())
        }
    }


}