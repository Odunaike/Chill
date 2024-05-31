package com.example.chill.presentation.Home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chill.domain.model.FavMovieModel
import com.example.chill.presentation.Authentication.AuthenticationViewModel
import com.example.chill.ui.theme.gradient_transparent
import com.example.chill.ui.theme.gradient_dark
import com.example.chill.ui.theme.gradient_mid_dark
import com.example.chill.ui.theme.gradient_mid_transparent
import com.example.chill.ui.theme.gradient_semi_transparent


@SuppressLint("MutableCollectionMutableState")
@Composable
fun MovieDetailsScreen(
    viewModel: HomeViewModel,
    authViewModeL: AuthenticationViewModel
){

    val result = viewModel.state.clivkedItem
    val userFavListRemote by remember {
        mutableStateOf(authViewModeL.retrieveUserFavList())
    }

    Box {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500${result!!.backdrop_path}")
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .height(159.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                gradient_transparent, gradient_semi_transparent,
                                gradient_mid_transparent, gradient_mid_dark, gradient_dark
                            )
                        )
                    )
            ) {

            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 158.dp)
        ) {
            Row (
                modifier = Modifier.padding(10.dp)
            ){
                AsyncImage(
                    modifier = Modifier
                        .padding(20.dp)
                        .size(width = 150.dp, height = 220.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w500${result!!.poster_path}")
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                )
                DetailColumn(
                    modifier = Modifier.padding(top = 25.dp),
                    title = result.title,
                    date = result.release_date,
                    vote = result.vote_count,
                    popularity = result.popularity

                )
            }
            Button(
                onClick = {
                    Log.i("foo", "${userFavListRemote.size}")
                    userFavListRemote.add(
                        FavMovieModel(
                            poster_path = result?.poster_path,
                            title = result?.title
                        )
                    )
                    authViewModeL.saveUserFavList(userFavListRemote)
                    Log.i("foo", "${userFavListRemote.size}")
                },
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Add to Favorites", modifier = Modifier.padding(end = 5.dp))
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = null )
            }
            Text(
                text = result?.overview ?: "no overview",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }

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
        Text(text = title ?: "--",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 25.dp)
        )
        Text(text = date ?: "--")
        Text(text = popularity.toString() ?: "--")
        Text(text = vote.toString() ?: "--")
    }
}