package com.example.printorderapp.data.repository

import android.content.Context
import com.example.printorderapp.domaine.model.Order
import com.google.gson.Gson
import kotlinx.coroutines.delay

class OrderRepository(private val context: Context) {

    suspend fun getOrder(orderId: String): Order {
        delay(1000) // Simulate network delay
        val json = context.assets.open("order.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(json, Order::class.java)
    }
}