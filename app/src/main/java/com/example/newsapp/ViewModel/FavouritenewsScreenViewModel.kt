package com.example.newsapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.newsapp.repo.ArticleRepoInterface
import javax.inject.Inject

class FavouritenewsScreenViewModel @Inject constructor(
    private val repo: ArticleRepoInterface
):ViewModel() {
}