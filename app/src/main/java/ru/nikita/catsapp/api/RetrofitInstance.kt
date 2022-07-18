package ru.nikita.catsapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nikita.catsapp.utils.URL

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}