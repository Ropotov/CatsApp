package ru.nikita.catsapp.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.nikita.catsapp.api.RetrofitInstance
import ru.nikita.catsapp.model.DeleteResponse
import ru.nikita.catsapp.model.FavoritesDataItem
import ru.nikita.catsapp.utils.API_KEY

class FavoritesViewModel : ViewModel() {

    var favoritesList: MutableLiveData<Response<ArrayList<FavoritesDataItem>>> = MutableLiveData()
    var deleteResponse: MutableLiveData<Response<DeleteResponse>> = MutableLiveData()

    fun getFavoritesList() {
        viewModelScope.launch {
            favoritesList.value = RetrofitInstance.api.getFavoriteList(API_KEY)
        }
    }
    fun deleteCarFromFavoritesList(string: String) {
        viewModelScope.launch {
            deleteResponse.value = RetrofitInstance.api.deleteCatFromFavoriteList(string,API_KEY)
        }
    }
}