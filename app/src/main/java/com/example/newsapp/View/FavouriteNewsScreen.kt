package com.example.newsapp.View

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun FavouriteNewsScreen(navController: NavController,apiKey:String){
    FavouriteScreenGenerate(navController = navController,apiKey)
}

@Composable
fun FavouriteScreenGenerate(navController: NavController,apiKey:String){

}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview(){
    FavouriteScreenGenerate(navController = NavController(LocalContext.current),"")
}