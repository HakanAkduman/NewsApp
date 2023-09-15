package com.example.newsapp.View

import android.inputmethodservice.Keyboard
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.Model.Article
import com.example.newsapp.Model.NewsResponse
import com.example.newsapp.Model.Source
import com.example.newsapp.R
import com.example.newsapp.ViewModel.AllNewsScreenViewModel
import javax.inject.Inject

@Composable
fun AllNewsScreen(navController: NavController,apiKey:String){
    AllNewsScreenGenerate(navController = navController,apiKey)
}

@Composable
fun AllNewsScreenGenerate  (navController: NavController,apiKey:String,viewModel: AllNewsScreenViewModel =hiltViewModel<AllNewsScreenViewModel>()){

    viewModel.loadAllNews(apiKey = apiKey)
    /*
    val exampleArticle= Article(

            author = "Hakan",
    content = "haberin tamamı burada",
    description = "it is description",
    publishedAt = "200202",
    source = Source(Any()," "),
    title = "ŞokHaver",
    url = "  ",
    urlToImage = "")
    viewModel.saveNew(LocalContext.current,exampleArticle)
*/
    val allNews by viewModel.allNews.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isError by viewModel.isError.observeAsState("")
    var prevError by remember { mutableStateOf("") }
    val context= LocalContext.current



    if (isLoading) {
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


    if (isError.isNotBlank() && prevError != isError) {
        Toast.makeText(LocalContext.current, isError, Toast.LENGTH_LONG).show()
        prevError = isError
    }

    Column(Modifier.fillMaxSize()) {
        if (allNews!=null){
            Text(text = "${allNews!!.totalResults} news have been found", color = Color.LightGray, modifier = Modifier.padding(15.dp))
            LazyColumn{
                items(allNews!!.articles){
                    NewItem(article = it){
                        viewModel.saveNew(context = context,it)
                    }
                }
            }
        }
    }

}


@Composable
fun NewItem(article:Article,onClick:(Article)->Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 15.dp)
            .background(
                Color.Gray,
                RoundedCornerShape(10.dp)
            )
    ) {
        Column(modifier=Modifier.weight(0.4f)) {
            Image(
                painter = painterResource(id = R.drawable.favourite_icon),
                contentDescription = "",
                modifier= Modifier
                    .size(40.dp)
                    .padding(vertical=3.dp, horizontal = 10.dp)
                    .clickable { onClick(article) }
            )
            Image(
                painter = rememberAsyncImagePainter(model = article.urlToImage),
                contentDescription = "",
                modifier= Modifier
                    .size(150.dp)
                    .padding(3.dp)
            )
            Text(
                text = article.author?:"There is no author",
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp),
                maxLines = 1
            )
            Text(
                text = article.publishedAt ?:"None",
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp),
                maxLines = 1
            )

        }
        Column(modifier=Modifier.weight(0.6f)) {
            Text(
                text = article.title?:"None",
                modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp),
                fontSize = 20.sp,
                maxLines = 1,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
            Text(text = article.content?:"None",
                modifier=Modifier.padding(
                vertical = 3.dp,
                horizontal = 10.dp
                ),
                fontSize = 16.sp,
            )

        }

    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview(){
    NewItem(article = Article(
        author = "Hakan",
        content = "haberin tamamı burada",
        description = "it is description",
        publishedAt = "200202",
        source = Source(Any()," "),
        title = "ŞokHaver",
        url = "  ",
        urlToImage = ""
    )){

    }
    //ScreenGenerate(navController = NavController(LocalContext.current),"")
}
