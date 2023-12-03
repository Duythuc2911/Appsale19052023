package com.example.appsale19052023.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsale19052023.data.api.AppResource
import com.example.appsale19052023.data.api.dto.AppResponseDTO
import com.example.appsale19052023.data.api.dto.CartDTO
import com.example.appsale19052023.data.api.dto.ProductDTO
import com.example.appsale19052023.data.model.Cart
import com.example.appsale19052023.data.model.Product
import com.example.appsale19052023.data.repository.CartRepository
import com.example.appsale19052023.data.repository.ProductRepository
import com.example.appsale19052023.util.CartUtils
import com.example.appsale19052023.util.ProductUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(context: Context) : ViewModel() {

    private val productRepository = ProductRepository(context)
    private val cartRepository = CartRepository(context)
    private val loadingLiveData = MutableLiveData<Boolean>()
    private val listProductsLiveData = MutableLiveData<AppResource<List<Product>>>()
    private val cartLiveData = MutableLiveData<AppResource<Cart>>()
    fun getLoading(): LiveData<Boolean> = loadingLiveData
    fun getListProducts(): LiveData<AppResource<List<Product>>> = listProductsLiveData
    fun getCart(): LiveData<AppResource<Cart>> = cartLiveData

    fun executeGetListProducts() {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            productRepository
                .requestListProducts()
                .enqueue(object : Callback<AppResponseDTO<List<ProductDTO>>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<List<ProductDTO>>>,
                        response: Response<AppResponseDTO<List<ProductDTO>>>
                    ) {
                        if (response.errorBody() != null) {
                            val errorResponse = response.errorBody()?.string() ?: "{}"
                            val jsonError = JSONObject(errorResponse)
                            listProductsLiveData.value =
                                AppResource.Error(jsonError.optString("message"))
                        } else {
                            val listProducts =
                                response.body()?.data?.map { ProductUtils.parseProductDTO(it) }
                            listProductsLiveData.value = AppResource.Success(listProducts)
                        }

                        loadingLiveData.value = false
                    }

                    override fun onFailure(
                        call: Call<AppResponseDTO<List<ProductDTO>>>,
                        t: Throwable
                    ) {
                        listProductsLiveData.value = AppResource.Error(t.message.toString())
                    }
                })
        }
    }

    fun executeGetCart() {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository
                .requestCart()
                .enqueue(object : Callback<AppResponseDTO<CartDTO>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<CartDTO>>,
                        response: Response<AppResponseDTO<CartDTO>>
                    ) {
                        if (response.errorBody() != null) {
                            if (response.code() == 500) {
                                cartLiveData.value = AppResource.Success(Cart())
                            } else {
                                val errorResponse = response.errorBody()?.string() ?: "{}"
                                val jsonError = JSONObject(errorResponse)
                                cartLiveData.value =
                                    AppResource.Error(jsonError.optString("message"))
                            }
                        } else {
                            val cart = CartUtils.parseCartDTO(response.body()?.data)
                            cartLiveData.value = AppResource.Success(cart)
                        }

                        loadingLiveData.value = false
                    }

                    override fun onFailure(
                        call: Call<AppResponseDTO<CartDTO>>,
                        t: Throwable
                    ) {
                        cartLiveData.value = AppResource.Error(t.message.toString())
                    }
                })
        }
    }

    fun executeAddToCart(idProduct: String) {
        loadingLiveData.value = true
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository
                .requestAddToCart(idProduct)
                .enqueue(object : Callback<AppResponseDTO<CartDTO>> {
                    override fun onResponse(
                        call: Call<AppResponseDTO<CartDTO>>,
                        response: Response<AppResponseDTO<CartDTO>>
                    ) {
                        if (response.errorBody() != null) {
                            if (response.code() == 500) {
                                cartLiveData.value = AppResource.Success(Cart())
                            } else {
                                val errorResponse = response.errorBody()?.string() ?: "{}"
                                val jsonError = JSONObject(errorResponse)
                                cartLiveData.value =
                                    AppResource.Error(jsonError.optString("message"))
                            }
                        } else {
                            val cart = CartUtils.parseCartDTO(response.body()?.data)
                            cartLiveData.value = AppResource.Success(cart)
                        }

                        loadingLiveData.value = false
                    }

                    override fun onFailure(
                        call: Call<AppResponseDTO<CartDTO>>,
                        t: Throwable
                    ) {
                        cartLiveData.value = AppResource.Error(t.message.toString())
                    }
                })
        }
    }

}