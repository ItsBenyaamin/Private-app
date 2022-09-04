package com.benyaamin.privateapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.benyaamin.privateapp.R
import com.benyaamin.privateapp.ui.screens.destinations.PasswordScreenDestination
import com.benyaamin.privateapp.ui.theme.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class HomeItems(val title: String, @DrawableRes val icon: Int)

@Destination(start = true)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column {
            MyTopAppBar()
            MyCards(navigator)
        }
    }
}

@Composable
fun MyTopAppBar() {
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .height(165.dp)) {
        val ref = createRef()
        TopAppBar(
            modifier = Modifier
                .clip(TopBarShape)
                .fillMaxSize(),
            backgroundColor = colorPrimary
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp),
                    text = "Friday, 26 August 2022",
                    color = onPrimary
                )
                Text(
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp),
                    text = "Hi, Benyamin Eskandari",
                    color = onPrimary
                )
                Text(
                    modifier = Modifier.padding(0.dp, 4.dp),
                    text = "Welcome To Your Secure Private Space",
                    color = Color.White,
                    style = Typography.h1
                )
            }
        }
        Icon(
            modifier = Modifier
                .constrainAs(ref) {
                    top.linkTo(parent.top, 16.dp)
                    end.linkTo(parent.end, 16.dp)
                }
                .size(32.dp)
                .padding(3.dp),
            tint = onPrimary,
            imageVector = Icons.Filled.Settings,
            contentDescription = "Setting"
        )
    }
}

@Composable
fun MyCards(navigator: DestinationsNavigator) {
    val mItems = arrayListOf(
        HomeItems(stringResource(R.string.passwords), R.drawable.ic_password),
        HomeItems(stringResource(R.string.tasks), R.drawable.ic_credit_card),
        HomeItems(stringResource(R.string.notes), R.drawable.ic_note),
        HomeItems(stringResource(R.string.accounting), R.drawable.ic_accounting),
    )
    LazyVerticalGrid(modifier = Modifier.fillMaxSize().padding(0.dp, 24.dp, 0.dp, 0.dp),
        columns = GridCells.Adaptive(110.dp),
        content = {
            items(4) { index ->
                Card(
                    Modifier
                        .padding(8.dp)
                        .shadow(4.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .clickable {
                                   when(index) {
                                       0 -> navigator.navigate(PasswordScreenDestination())
                                       1 -> navigator.navigate(PasswordScreenDestination())
                                       2 -> navigator.navigate(PasswordScreenDestination())
                                       3 -> navigator.navigate(PasswordScreenDestination())
                                   }
                        },
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = mItems[index].icon),
                            contentDescription = mItems[index].title
                        )
                        Text(
                            modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 0.dp),
                            text = mItems[index].title,
                            color = colorPrimary,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        })
}

@Preview
@Composable
fun HomeScreenPreview() {
    PrivateTheme {
//        HomeScreen()
    }
}
