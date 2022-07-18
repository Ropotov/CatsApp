package ru.nikita.catsapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nikita.catsapp.model.DataModelItem

interface ApiService {
    @GET("search")
    suspend fun getCatsList(@Query("api_key") apiKey: String): Response<ArrayList<DataModelItem>>
}