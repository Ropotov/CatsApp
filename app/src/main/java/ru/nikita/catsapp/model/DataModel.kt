package ru.nikita.catsapp.model

data class DataModel (val item: ArrayList<DataModelItem>)

data class DataModelItem(
    var height: Int?,
    var id: String?,
    var url: String?,
    var width: Int?
)