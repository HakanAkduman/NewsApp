package com.example.newsapp.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.example.newsapp.api.ArticleApi
import com.example.newsapp.database.ArticleDatabase
import com.example.newsapp.ui.theme.Strings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context)= Room.databaseBuilder(
        context = context,
        ArticleDatabase::class.java,
        "ArticleDatabase"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArticleDatabase)=database.getArticleDao()

    @Singleton
    @Provides
    fun injectRetrofit():ArticleApi{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Strings.BASE_URL)
            .build()
            .create(ArticleApi::class.java)
    }
}