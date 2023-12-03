package com.example.appsale19052023.data.repository

import android.content.Context
import com.example.appsale19052023.data.api.RetrofitClient
import com.example.appsale19052023.data.api.dto.AppResponseDTO
import com.example.appsale19052023.data.api.dto.ProductDTO
import retrofit2.Call

class ProductRepository(
    var context: Context
) {
    private val apiService = RetrofitClient.getApiService(context)

    fun requestListProducts(): Call<AppResponseDTO<List<ProductDTO>>> {
        return apiService.getListProducts()
    }
}