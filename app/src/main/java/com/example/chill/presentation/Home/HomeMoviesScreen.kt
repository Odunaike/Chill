@file:OptIn(ExperimentalFoundationApi::class)

package com.example.chill.presentation.Home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chill.R
import com.example.chill.domain.model.Result
import com.example.chill.presentation.CurrentUser
import kotlinx.coroutines.delay


@Composable
fun MovieItemCard(
    modifier: Modifier = Modifier,
    imgPath: String,
    result: Result,
    onItemSelected: (Result) -> Unit,
) {
    Card(
        modifier = modifier
            .size(width = 150.dp, height = 220.dp)
            .clickable(
                enabled = true,
                onClick = {
                    onItemSelected(result)
                }
            ),
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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeMoviesScreen(
    currentUser: CurrentUser,
    viewModel: HomeViewModel ,
    onItemSelected: (Result) -> Unit
) {
    Column {
        Text(
            text = "Hello, ${currentUser.username}",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        )
        MovieCarousel()
        var myList: List<Result> = viewModel.state.movieList
        Text(
            text = "Discover",
            fontSize = 30.sp
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(myList) {
                it.poster_path?.let { img_path ->
                    MovieItemCard(
                        modifier = Modifier.padding(8.dp),
                        imgPath = img_path,
                        result = it,
                        onItemSelected = onItemSelected
                    )
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MovieCarousel(){
    val imageList = listOf(
        R.drawable.movie1,
        R.drawable.movie2,
        R.drawable.movies3
    )
    val pagerState = rememberPagerState(pageCount = {3})
    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(360.dp),
        pageSpacing = 20.dp,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp)),
            painter = painterResource(id = imageList[it]),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
    }
    //row with circle indicator for the current page
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(pagerState.pageCount){
            val color = if (pagerState.currentPage == it) Color.Magenta else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(10.dp)
            )
        }
    }
    LaunchedEffect(key1 = pagerState.settledPage,){
        delay(3000)
        with(pagerState){
            val target = if (settledPage < pageCount-1) settledPage+1 else 0
            animateScrollToPage(
                page = target,
                animationSpec = tween(durationMillis = 1000)
            )
        }
    }
}