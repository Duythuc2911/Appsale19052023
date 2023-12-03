package com.example.appsale19052023.data.api.dto

import com.google.gson.annotations.SerializedName

data class ProductDTO(
    @SerializedName("_id")
    var id: String?,
    var name: String?,
    var address: String?,
    var price: Long,
    var img: String?,
    var quantity: Int?,
    @SerializedName("gallery")
    var listGallery: List<String>?,
    @SerializedName("date_created")
    var dateCreated: String?,
    @SerializedName("date_updated")
    var dateUpdated: List<String>?,
    @SerializedName("__v")
    var v: Int?
)