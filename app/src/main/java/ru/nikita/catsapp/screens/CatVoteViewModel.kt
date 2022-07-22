package ru.nikita.catsapp.screens

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.nikita.catsapp.api.RetrofitInstance
import ru.nikita.catsapp.model.DataModelItem
import ru.nikita.catsapp.model.PostItem
import ru.nikita.catsapp.model.PostResponse
import ru.nikita.catsapp.utils.API_KEY

class CatVoteViewModel : ViewModel() {
    var catList: MutableLiveData<Response<ArrayList<DataModelItem>>> = MutableLiveData()
    val postList: MutableLiveData<Response<PostResponse>> = MutableLiveData()


    fun getCat() {
        viewModelScope.launch {
            catList.value = RetrofitInstance.api.getCatsList(API_KEY)
        }
    }

    fun postCat(postItem: PostItem) {
        viewModelScope.launch {
            postList.value = RetrofitInstance.api.postCatFavorite(API_KEY, postItem)
        }
    }
}