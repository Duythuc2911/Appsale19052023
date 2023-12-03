package com.example.appsale19052023.util

import com.example.appsale19052023.data.api.dto.UserDTO
import com.example.appsale19052023.data.model.User

class UserUtils {

    companion object {
        fun parseUserDTO(userDTO: UserDTO?): User {
            return User(
                email = userDTO?.email ?: "",
                name = userDTO?.name ?: "",
                phone = userDTO?.phone ?: "",
                token = userDTO?.token ?: "",
            )
        }
    }
}