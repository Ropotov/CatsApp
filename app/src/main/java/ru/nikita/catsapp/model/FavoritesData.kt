package ru.nikita.catsapp.model

data class FavoritesData(
    var catList: ArrayList<FavoritesDataItem>
)

data class FavoritesDataItem(
    var created_at: String,
    var id: Int,
    var image: ImageData,
    var image_id: String,
    var sub_id: Any,
    var user_id: String
)

data class ImageData(
    var id: String,
    var url: String
)