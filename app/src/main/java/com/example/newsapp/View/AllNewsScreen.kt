package com.example.newsapp.View

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun AllNewsScreen(navController: NavController){
    ScreenGenerate(navController = navController)
}

@Composable
private fun ScreenGenerate(navController: NavController){
    
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview(){
    ScreenGenerate(navController = NavController(LocalContext.current))
}
