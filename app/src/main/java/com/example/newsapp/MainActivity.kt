package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.View.AllNewsScreen
import com.example.newsapp.View.FavouriteNewsScreen
import com.example.newsapp.View.SearchNewsScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint


lateinit var activity: MainActivity
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        activity=this
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                MainSchema()
            }
        }
    }
}

fun startActivity(intent: Intent){
    activity.let {
        it.startActivity(intent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainSchema(){
    var selected by remember{ mutableStateOf(0) }
    var apiKey :String? by remember {
        mutableStateOf(null)
    }
    FirebaseDatabase.getInstance().reference.child("util").child("apiKey").get().addOnSuccessListener {
        apiKey=it.getValue(String::class.java)
    }

    val navController= rememberNavController()

    if(apiKey==null){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp),
                color = Color.Cyan
            )
        }
    }else{
        Scaffold(
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.LightGray,
                            RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                        ),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    ImageButton(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(50.dp)
                            .background(
                                if (selected == 0) {
                                    Color.Red
                                } else {
                                    Color.Cyan
                                }, CircleShape
                            ),
                        painter = painterResource(id =R.drawable.home_icon)
                    ) {
                        selected=0
                        navController.navigate("AllNewsScreen")
                    }
                    ImageButton(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(50.dp)
                            .background(
                                if (selected == 1) {
                                    Color.Red
                                } else {
                                    Color.Cyan
                                }, CircleShape
                            ),
                        painter = painterResource(id = R.drawable.search_icon)
                    ) {
                        selected=1
                        navController.navigate("SearchNewsScreen")
                    }
                    ImageButton(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(50.dp)
                            .background(
                                if (selected == 2) {
                                    Color.Red
                                } else {
                                    Color.Cyan
                                }, CircleShape
                            ),
                        painter = painterResource(id = R.drawable.favourite_icon)
                    ) {
                        selected=2
                        navController.navigate("FavouriteNewsScreen")
                    }
                }
            }
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {

                NavHost(navController = navController, startDestination = "AllNewsScreen"){
                    composable("AllNewsScreen"){
                        AllNewsScreen(navController = navController, apiKey = apiKey!!)
                    }
                    composable("SearchNewsScreen"){
                        SearchNewsScreen(navController = navController, apiKey = apiKey!!)
                    }
                    composable("FavouriteNewsScreen"){
                        FavouriteNewsScreen(navController = navController, apiKey = apiKey!!)
                    }
                }
            }
        }
    }


}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    MainSchema()
}

@Composable
fun ImageButton(modifier:Modifier, painter: Painter, description:String?=null, enabled:Boolean=true, onClick: () -> Unit) {
    Surface(
        modifier = modifier.clickable(onClick = onClick, enabled = enabled),
        color = Color.Transparent
    ) {
        Image(
            painter = painter,
            contentDescription = description,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentScale = ContentScale.Fit
        )
    }
}