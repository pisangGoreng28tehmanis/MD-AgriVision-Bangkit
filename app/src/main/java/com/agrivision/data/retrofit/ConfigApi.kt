package com.example.agrivision.api

import com.agrivision.data.retrofit.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ConfigApi {
    private const val BASE_URL = "https://agrivision-api-59998104202.asia-southeast2.run.app/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
