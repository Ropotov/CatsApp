package ru.nikita.catsapp.api

import retrofit2.Response
import retrofit2.http.*
import ru.nikita.catsapp.model.*
import ru.nikita.catsapp.utils.API_KEY

interface ApiService {
    @GET("images/search")
    suspend fun getCatsList(
        @Query("api_key") apiKey: String
    ): Response<ArrayList<DataModelItem>>

    @POST("favourites")
    suspend fun postCatFavorite(
        @Query("api_key") apiKey: String,
        @Body postItem: PostItem
    ): Response<PostResponse>

    @GET("favourites")
    suspend fun getFavoriteList(
        @Query("api_key") apiKey: String
    ): Response<ArrayList<FavoritesDataItem>>


    @DELETE("favourites/{favorite_id}")
    suspend fun deleteCatFromFavoriteList(
        @Path("favorite_id") favoriteId: String,
        @Header("x-api-key") apiKey: String
    ): Response<DeleteResponse>
}