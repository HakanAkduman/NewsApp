package com.example.newsapp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.newsapp.repo.ArticleRepoInterface
import javax.inject.Inject

class SearchNewsScreenViewModel @Inject constructor(
    private val repo:ArticleRepoInterface
):ViewModel() {
}