package com.example.newsapp.View

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.ViewModel.SearchNewsScreenViewModel
import java.util.Locale

@Composable
fun SearchNewsScreen(navController: NavController,apiKey:String){
    SearchScreenGenerate(navController = navController,apiKey)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenGenerate(navController: NavController,apiKey:String,viewModel: SearchNewsScreenViewModel= hiltViewModel()){

    val news by viewModel.searchedNews.observeAsState(listOf())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isError by viewModel.isError.observeAsState("")
    var prevError by remember { mutableStateOf("") }
    val context= LocalContext.current

    var search by remember{ mutableStateOf("") }


    if (isError.isNotBlank() && prevError != isError) {
        Toast.makeText(LocalContext.current, isError, Toast.LENGTH_LONG).show()
        prevError = isError
    }

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = search,
                onValueChange = { search = it.toLowerCase(Locale.ENGLISH) },
                modifier = Modifier.weight(3f),
                label = { Text(text = "Search") },
                singleLine = true,
                maxLines = 1
            )
           Spacer(modifier = Modifier.size(10.dp))
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (search.isNotBlank()) {
                        viewModel.searchNews(apiKey, search)
                    }
                }
            ) {
                Text(text = "Search")
            }
        }
    }) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

            if(isLoading){
                prevError=""

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = 1f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(100.dp)
                        ,
                        color = Color.White
                    )
                }
            }

            LazyColumn{
                items(news){
                    NewItem(painter = painterResource(id = R.drawable.favourite_icon),article = it){
                        viewModel.saveNew(context = context,it)
                    }
                }
            }

        }


    }


}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview(){
    SearchScreenGenerate(navController = NavController(LocalContext.current),"")
}