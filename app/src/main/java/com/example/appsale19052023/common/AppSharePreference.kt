package com.example.appsale19052023.common

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class AppSharePreference(
    context: Context
) {
    private val SHARED_PREFERENCE_NAME = "app-cache"

    companion object {
        val TOKEN_KEY = "token"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFERENCE_NAME, AppCompatActivity.MODE_PRIVATE)
    }

    private val editor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    fun saveString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(key: String) = sharedPreferences.getString(key, "")
}