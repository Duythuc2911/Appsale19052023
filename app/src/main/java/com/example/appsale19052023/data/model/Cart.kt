package com.example.appsale19052023.data.model

data class Cart(
    var id: String = "",
    var listProduct: List<Product> = emptyList(),
    var idUser: String = "",
    var price: Long = 0,
    var dateCreated: String = "",
)