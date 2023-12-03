package com.example.appsale19052023.data.api

sealed class AppResource<out T: Any> {
    data class Success<out T: Any>(val data: T?): AppResource<T>()
    data class Error(val error: String): AppResource<Nothing>()
}