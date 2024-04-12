package com.example.chill.presentation.Home

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.chill.R
import com.example.chill.data.remote.MovieApi
import com.example.chill.domain.model.MovieItem
import com.example.chill.domain.model.Result
import kotlinx.coroutines.launch


@Composable
fun MovieItemCard(
    modifier: Modifier = Modifier,
    imgPath: String,
) {
    Card(
        modifier = modifier
            .size(width = 150.dp, height = 220.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
       // Text(text = imgPath)
        AsyncImage(
            //modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500$imgPath")
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun MovieGrid(
    viewModel: HomeViewModel = hiltViewModel()
) {
    var myList: List<Result> = viewModel.state.movieList

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ) {
        items(myList) {
            it.poster_path?.let { img_path ->
                MovieItemCard(
                    modifier = Modifier.padding(8.dp),
                    imgPath = img_path
                )
            }
            
        }
    }
}