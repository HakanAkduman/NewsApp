package com.example.newsapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.NewsResponse
import com.example.newsapp.repo.ArticleRepo
import com.example.newsapp.repo.ArticleRepoInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritenewsScreenViewModel @Inject constructor(
    private val repo: ArticleRepo
):ViewModel() {

    private val _allNews = MutableLiveData<List<Article>>()
    val allNews : LiveData<List<Article>> =_allNews

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading : LiveData<Boolean> =_isLoading

    private val _isError = MutableLiveData("")
    val isError : LiveData<String> =_isError

    fun loadFavourites(){
        viewModelScope.launch {
            val value=repo.getAllArticles()
            _allNews.value=value.value
        }
    }


}