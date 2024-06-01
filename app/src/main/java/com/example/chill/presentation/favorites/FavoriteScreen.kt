package com.example.chill.presentation.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chill.R
import com.example.chill.domain.model.FavMovieModel
import com.example.chill.presentation.Authentication.AuthenticationViewModel


@Composable
fun FavoritesScreen(
    authViewModel: AuthenticationViewModel
){
    val userFavListRemote by remember {
        mutableStateOf(authViewModel.retrieveUserFavList())
    }

    Column {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Favorites",
            fontSize = 33.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        FavList(userFavListRemote.toList())
    }
}

@Composable
fun FavList(favList: List<FavMovieModel>){
    LazyColumn(){
        items(
            items = favList
        ){
            FavItem(it)
        }
    }
}

@Composable
fun FavItem(favItem: FavMovieModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${favItem.poster_path}")
                .build(),
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .size(100.dp),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
         Text(
             modifier = Modifier.padding(start = 20.dp),
             color = MaterialTheme.colorScheme.onPrimaryContainer,
             text = favItem.title ?: "",
             fontSize = 20.sp
         )
    }
}