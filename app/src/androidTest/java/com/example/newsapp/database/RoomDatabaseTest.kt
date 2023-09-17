package com.example.newsapp.database

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.annotation.ExperimentalTestApi
import com.google.common.truth.Truth.assertThat
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.Source
import com.example.newsapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@SmallTest
@ExperimentalCoroutinesApi
class RoomDatabaseTest {
    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()
    private lateinit var database:ArticleDatabase

    @Before
    fun setup(){
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),ArticleDatabase::class.java
        ).allowMainThreadQueries().build()

    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertArticleTesting()= runBlocking{
        val exampleArticle= Article(
            author = "Hakan",
            content = "haberin tamamı burada",
            description = "it is description",
            publishedAt = "200202",
            source = Source(Any()," "),
            title = "ŞokHaver",
            url = "  ",
            urlToImage = ""
        )
        database.getArticleDao().insert(exampleArticle)
        val list = database.getArticleDao().getAllArticles()

        assertThat(list).contains(exampleArticle)



    }





}