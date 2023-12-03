package com.example.appsale19052023.util

import java.text.DecimalFormat

object StringUtils {
    fun formatCurrency(number: Int): String {
        return DecimalFormat("#,###").format(number)
    }
}