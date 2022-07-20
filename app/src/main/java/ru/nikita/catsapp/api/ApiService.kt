package ru.nikita.catsapp.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.nikita.catsapp.model.DataModelItem

interface ApiService {
    @GET("images/search")
    suspend fun getCatsList(@Query("api_key") apiKey: String): Response<ArrayList<DataModelItem>>

    @POST("favourites")
    suspend fun postCatFavorite(@Query("api_key") apiKey: String, @Body() string: String?)
}