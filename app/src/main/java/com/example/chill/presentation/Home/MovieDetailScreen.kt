package com.example.chill.presentation.Home

import android.view.textclassifier.TextClassifierEvent.LanguageDetectionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chill.R


@Composable
fun MovieDetailsScreen(
    viewModel: HomeViewModel
){

    val result = viewModel.state.clivkedItem

    Column {
        AsyncImage(
            modifier = Modifier.fillMaxWidth().height(250.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${result!!.backdrop_path}")
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        Row (
            modifier = Modifier.padding(10.dp)
        ){
            AsyncImage(
                modifier = Modifier.size(width = 150.dp, height = 220.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500${result!!.poster_path}")
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
            DetailColumn(
                title = result.title,
                date = result.release_date,
                vote = result.vote_count,
                popularity = result.popularity

            )
        }
        Text(text = result.overview ?: "no overview")
    }
}
@Composable
fun DetailColumn(
    modifier: Modifier = Modifier,
    title: String?,
    popularity: Double?,
    date: String?,
    vote: Int?
){
    Column(modifier = modifier) {
        Text(text = title ?: "--")
        Text(text = date ?: "--")
        Text(text = popularity.toString() ?: "--")
        Text(text = vote.toString() ?: "--")
    }
}