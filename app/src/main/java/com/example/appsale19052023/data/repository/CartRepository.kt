package com.example.appsale19052023.data.repository

import android.content.Context
import com.example.appsale19052023.data.api.RetrofitClient
import com.example.appsale19052023.data.api.dto.AppResponseDTO
import com.example.appsale19052023.data.api.dto.CartDTO
import retrofit2.Call

class CartRepository(
    var context: Context
) {
    private val apiService = RetrofitClient.getApiService(context)

    fun requestCart(): Call<AppResponseDTO<CartDTO>> {
        return apiService.getCart()
    }

    fun requestAddToCart(idProduct: String): Call<AppResponseDTO<CartDTO>> {
        val map = HashMap<String, Any>()
        map["id_product"] = idProduct
        return apiService.addCart(map)
    }
}