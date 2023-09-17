package com.example.newsapp.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Model.Article
import com.example.newsapp.repo.ArticleRepo
import com.example.newsapp.repo.ArticleRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchNewsScreenViewModel @Inject constructor(
    private val repo:ArticleRepo
):ViewModel() {

    private val _searchedNews = MutableLiveData<List<Article>>()
    val searchedNews : LiveData<List<Article>> =_searchedNews

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading : LiveData<Boolean> =_isLoading

    private val _isError = MutableLiveData("")
    val isError : LiveData<String> =_isError

    fun searchNews(apiKey:String,searchQuery:String){
        _isLoading.value=true
        viewModelScope.launch {
            val response=repo.searchForNews(apiKey,searchQuery)
            if (response.isSuccessful){
                _searchedNews.value=response.body()?.articles
            }else{
                _isError.value=response.errorBody().toString()
            }
            _isLoading.value=false
        }
    }
    fun saveNew(context: Context, new:Article)=
        viewModelScope.launch {
            repo.insert(article = new)
            Toast.makeText(context,"This new has been saved", Toast.LENGTH_LONG).show()
        }

}