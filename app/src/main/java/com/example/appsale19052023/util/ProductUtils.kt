package com.example.appsale19052023.util

import com.example.appsale19052023.data.api.dto.ProductDTO
import com.example.appsale19052023.data.model.Product

class ProductUtils {
    companion object {
        fun parseProductDTO(productDTO: ProductDTO?): Product {
            return Product(
                id = productDTO?.id ?: "",
                name = productDTO?.name ?: "",
                address = productDTO?.address ?: "",
                price = productDTO?.price ?: 0,
                img = productDTO?.img ?: "",
                quantity = productDTO?.quantity ?: 0,
                listGallery = productDTO?.listGallery ?: emptyList(),
            )
        }
    }
}