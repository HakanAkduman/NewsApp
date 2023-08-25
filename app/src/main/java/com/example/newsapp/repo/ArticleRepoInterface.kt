package com.example.newsapp.repo

import androidx.lifecycle.LiveData

import com.example.newsapp.Model.Article
import com.example.newsapp.Model.NewsResponse
import com.example.newsapp.Util.Resource
import retrofit2.Response


interface ArticleRepoInterface {


    suspend fun upsert(article: Article)

    fun getAllArticles(): LiveData<List<Article>>

    suspend fun deleteArticle(article: Article)

    suspend fun getBreakingNews(
        API_KEY: String
    ): Resource<NewsResponse>


    suspend fun searchForNews(
        API_KEY: String,
        searchQuery:String
    ): Resource<NewsResponse>
}