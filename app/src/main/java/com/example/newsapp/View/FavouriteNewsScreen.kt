package com.example.newsapp.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.ViewModel.FavouritenewsScreenViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun FavouriteNewsScreen(navController: NavController,apiKey:String){
    FavouriteScreenGenerate(navController = navController,apiKey)
}

@Composable
fun FavouriteScreenGenerate(navController: NavController,apiKey:String,viewModel: FavouritenewsScreenViewModel = hiltViewModel()){
    viewModel.loadFavourites()

    val allNews by viewModel.allNews.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isError by viewModel.isError.observeAsState("")
    var prevError by remember { mutableStateOf("") }
    val context= LocalContext.current

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (allNews==null || allNews!!.isEmpty()){
            Text(text = "There is nothing to show", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
        }else{
            LazyColumn{
                items(allNews!!){
                    NewItem(article = it){

                    }
                }
            }
        }
    }



}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview(){
    FavouriteScreenGenerate(navController = NavController(LocalContext.current),"")
}