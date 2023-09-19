package com.example.newsapp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.Source
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch


@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomDatabaseTest {


    private lateinit var database: ArticleDatabase
    private lateinit var dao: ArticleDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ArticleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dao = database.getArticleDao()
    }

    @After
    @Throws(IOException::class)
    fun teardown() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertArticleTesting(){
        runBlocking {
            val exampleArticle = Article(
                author = "Hakan",
                content = "haberin tamamı burada",
                description = "it is description",
                publishedAt = "200202",
                source = Source("", ""),
                title = "ŞokHaver",
                url = "",
                urlToImage = ""
            )

            dao.insert(exampleArticle)



            val latch = CountDownLatch(1)
            val job = async(Dispatchers.IO) {
                val list = dao.getAllArticles()

                assertThat(list).contains(exampleArticle)
            }
            withContext(Dispatchers.IO) {
                latch.await()
            }
            job.cancelAndJoin()


        }
    }
}