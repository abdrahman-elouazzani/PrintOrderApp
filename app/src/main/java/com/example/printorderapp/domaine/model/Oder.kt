package com.example.printorderapp.domaine.model

import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("customer")
    val customer: Customer?,
    @SerializedName("products")
    val products: List<Product>?,
    @SerializedName("totalAmount")
    val totalAmount: Double?
)

data class Customer(
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("address")
    val address: String?
)

data class Product(
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("quantity")
    val quantity: Int?
)
