package com.example.printorderapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.printorderapp.data.repository.OrderRepository
import com.example.printorderapp.domaine.state.OrderState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderDetailViewModel(private val orderRepository: OrderRepository): ViewModel() {

    private var _orderStateLD = MutableLiveData<OrderState>()
    val orderStateLD: LiveData<OrderState> = _orderStateLD

    private var orderId: String? = null

    fun initArgs(orderId: String?) {
        this.orderId = orderId
    }

    fun getOrder() {
        _orderStateLD.value = OrderState.Loading
        // for asynchronous processing
        viewModelScope.launch {
            try {
                orderId?.let { id ->
                    // switch to the IO dispatcher for network operations
                    val order = withContext(Dispatchers.IO) {
                        orderRepository.getOrder(
                            orderId = id
                        )
                    }
                    _orderStateLD.value = OrderState.Success(order)
                } ?: run {
                    _orderStateLD.value = OrderState.Error("Invalid order ID")
                }

            } catch (e: Exception) {
                _orderStateLD.value =
                    OrderState.Error(e.message ?: "Failed to load order: ${e.message}")
            }
        }
    }

}