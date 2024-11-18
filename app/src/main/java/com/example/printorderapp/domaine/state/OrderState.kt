package com.example.printorderapp.domaine.state

import com.example.printorderapp.domaine.model.Order

sealed class OrderState {
    object Loading : OrderState()
    data class Success(val order: Order) : OrderState()
    data class Error(val message: String) : OrderState()
}
