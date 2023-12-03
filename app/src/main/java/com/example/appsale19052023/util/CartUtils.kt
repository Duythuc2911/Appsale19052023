package com.example.appsale19052023.util

import com.example.appsale19052023.data.api.dto.CartDTO
import com.example.appsale19052023.data.model.Cart

class CartUtils {
    companion object {
        fun parseCartDTO(cartDTO: CartDTO?): Cart {
            return Cart(
                id = cartDTO?.id ?: "",
                idUser = cartDTO?.idUser ?: "",
                price = cartDTO?.price ?: 0,
                dateCreated = cartDTO?.dateCreated ?: "",
                listProduct = cartDTO?.listProductDTO?.map { ProductUtils.parseProductDTO(it) } ?: emptyList()
            )
        }
    }
}