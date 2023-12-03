package com.example.appsale19052023.data.api

import com.example.appsale19052023.data.api.dto.AppResponseDTO
import com.example.appsale19052023.data.api.dto.CartDTO
import com.example.appsale19052023.data.api.dto.ProductDTO
import com.example.appsale19052023.data.api.dto.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("user/sign-in")
    fun signIn(@Body map: HashMap<String, Any>): Call<AppResponseDTO<UserDTO>>

    @POST("user/sign-up")
    fun signUp(@Body map: HashMap<String, Any>): Call<AppResponseDTO<UserDTO>>

    @GET("product")
    fun getListProducts(): Call<AppResponseDTO<List<ProductDTO>>>

    @GET("cart")
    fun getCart(): Call<AppResponseDTO<CartDTO>>

    @POST("cart/add")
    fun addCart(@Body map: HashMap<String, Any>): Call<AppResponseDTO<CartDTO>>

    @POST("order history")
    fun History(@Body map: HashMap<String, Any>): Call<AppResponseDTO<CartDTO>>
}