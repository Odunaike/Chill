package com.example.chill.presentation.Account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chill.R
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AccountScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(CircleShape)
                .size(120.dp),
            painter = painterResource(id = R.drawable.profile_placeholder),
            contentDescription = "profile picture"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Odunaike David", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(70.dp))
        Text(
            text = "Settings",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(5.dp),
            textAlign = TextAlign.Start
        )
        UtilityList()
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UtilityList(){
    Column(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.Gray)
    ){
        LazyColumn(){
            items(items = utilityList){
                UtilityItem()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityItem(){
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                modifier = Modifier
                    .background(color = Color.Green, shape = CircleShape)
            ){
                Icon(imageVector = Icons.Rounded.Home, contentDescription = null, tint = Color.White)
            }
            Text(
                text = "Log Out",
                fontSize = 18.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp)
            )
            Icon(imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = null )
        }
        Divider()

    }

}

private val utilityList: List<UtilityItem> = listOf(

    UtilityItem(
        id = 1,
        icon = Icons.Rounded.AccountCircle,
        title = "Edit Profile"
    ),
    UtilityItem(
        id = 2,
        icon = Icons.Rounded.Email,
        title = "Contact Developer"
    ),
    UtilityItem(
        id = 3,
        icon = Icons.Rounded.Refresh,
        title = "Update App"
    ),
    UtilityItem(
        id = 4,
        icon = Icons.Default.ExitToApp,
        title = "Log Out"
    ),
)