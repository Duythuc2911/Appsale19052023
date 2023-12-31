package com.example.appsale19052023.data.api

import android.content.Context
import com.example.appsale19052023.common.AppConstant
import com.example.appsale19052023.common.AppSharePreference
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var instance: Retrofit? = null
    fun getApiService(context: Context): ApiService {
        if (instance == null) {
            instance = createRetrofit(context)
        }
        return instance!!.create(ApiService::class.java)
    }

    private fun createRetrofit(context: Context): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BASIC)
            })
            .addInterceptor(Interceptor { chain ->
                val token = AppSharePreference(context).getString(AppSharePreference.TOKEN_KEY)
                val request = chain.request().newBuilder()
                if (!token.isNullOrEmpty()) {
                    request.addHeader("Authorization", "Bearer $token")
                }
                return@Interceptor chain.proceed(request.build())
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
}