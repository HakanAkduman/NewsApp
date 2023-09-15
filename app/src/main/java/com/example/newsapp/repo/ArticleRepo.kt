package com.example.newsapp.repo

import androidx.lifecycle.LiveData
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.NewsResponse
import com.example.newsapp.Util.Resource
import com.example.newsapp.api.ArticleApi
import com.example.newsapp.database.ArticleDao
import com.example.newsapp.database.ArticleDatabase
import retrofit2.Response
import javax.inject.Inject


class ArticleRepo @Inject constructor(
    private val articleDao: ArticleDatabase,
    private val articleApi: ArticleApi
)  :ArticleRepoInterface {
    override suspend fun insert(article: Article){
        articleDao.getArticleDao().insert(article)
    }

    override fun getAllArticles(): LiveData<List<Article>> {
        return articleDao.getArticleDao().getAllArticles()
    }

    override suspend fun deleteArticle(article: Article) {
        articleDao.getArticleDao().deleteArticle(article)
    }

    override suspend fun getBreakingNews(API_KEY: String): Response<NewsResponse> {
        return articleApi.getBreakingNews(apiKey = API_KEY)

    }

    override suspend fun searchForNews(API_KEY: String,searchQuery:String): Response<NewsResponse> {
        return articleApi.searchForNews(apiKey = API_KEY, searchQuery = searchQuery)


    }
}