package com.example.newsapp.repo

import androidx.lifecycle.LiveData
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.NewsResponse
import com.example.newsapp.Util.Resource
import com.example.newsapp.api.ArticleApi
import com.example.newsapp.database.ArticleDao
import retrofit2.Response
import javax.inject.Inject


class ArticleRepo @Inject constructor(
    private val articleDao: ArticleDao,
    private val articleApi: ArticleApi
)  :ArticleRepoInterface {
    override suspend fun upsert(article: Article){
        articleDao.upsert(article)
    }

    override fun getAllArticles(): LiveData<List<Article>> {
        return articleDao.getAllArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        articleDao.deleteArticle(article)
    }

    override suspend fun getBreakingNews(API_KEY: String): Response<NewsResponse> {
        return articleApi.getBreakingNews(apiKey = API_KEY)

    }

    override suspend fun searchForNews(API_KEY: String,searchQuery:String): Response<NewsResponse> {
        return articleApi.searchForNews(apiKey = API_KEY, searchQuery = searchQuery)


    }
}