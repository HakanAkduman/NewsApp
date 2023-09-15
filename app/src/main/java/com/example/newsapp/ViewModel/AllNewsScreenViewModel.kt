package com.example.newsapp.ViewModel


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.NewsResponse
import com.example.newsapp.Util.Resource
import com.example.newsapp.Util.Status
import com.example.newsapp.api.ArticleApi
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.repo.ArticleRepo
import com.example.newsapp.repo.ArticleRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class AllNewsScreenViewModel @Inject constructor(
   private val repo: ArticleRepo
):ViewModel() {


    private val _allNews =MutableLiveData<NewsResponse>()
    val allNews : LiveData<NewsResponse> =_allNews

    private val _isLoading =MutableLiveData<Boolean>(false)
    val isLoading : LiveData<Boolean> =_isLoading

    private val _isError =MutableLiveData("")
    val isError : LiveData<String> =_isError

    fun loadAllNews(apiKey:String){

        _isLoading.value=true
        _isError.value=""
        viewModelScope.launch {
            val response=repo.getBreakingNews(apiKey)
            _isLoading.value=false
            if (response.isSuccessful){
                _allNews.value=response.body()!!
                return@launch
            }else {
                _isError.value=response.errorBody().toString()
                return@launch
            }
        }

    }
    fun saveNew(context:Context,new:Article)=
        viewModelScope.launch {
            repo.insert(article = new)
            Toast.makeText(context,"This new has been saved",Toast.LENGTH_LONG).show()
        }


}