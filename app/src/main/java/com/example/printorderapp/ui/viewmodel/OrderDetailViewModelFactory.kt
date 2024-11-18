package com.example.printorderapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.printorderapp.data.repository.OrderRepository

class OrderDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrderDetailViewModel(OrderRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
