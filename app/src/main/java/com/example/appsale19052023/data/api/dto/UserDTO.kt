package com.example.appsale19052023.data.api.dto

data class UserDTO(
    var email: String?,
    var name: String?,
    var phone: String?,
    var userGroup: Int?,
    var registerDate: String?,
    var token: String?
)