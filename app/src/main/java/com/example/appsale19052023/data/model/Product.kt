package com.example.appsale19052023.data.model

import java.io.Serializable

data class Product(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var price: Long = 0,
    var img: String = "",
    var quantity: Int = 0,
    var listGallery: List<String> = emptyList(),
): Serializable