package com.benyaamin.privateapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.benyaamin.privateapp.R
import com.benyaamin.privateapp.extensions.noRippleClickable
import com.benyaamin.privateapp.ui.theme.Typography

enum class AppbarState {
    Default,
    Search
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToolbarWithSearch(
    title: String,
    onBackClick: () -> Unit,
    onSearchQueryChanged: (query: String) -> Unit
) {
    val state = rememberToolbarState()
    val softwareKeyboardController = LocalSoftwareKeyboardController.current
    Box(modifier = Modifier) {
        if (state.value == AppbarState.Default)
            DefaultTopAppBar(
                title = title,
                onBackClick = onBackClick,
                icon = {
                    Icon(
                        modifier = Modifier
                            .padding(start = 16.dp, 0.dp, 16.dp, 0.dp)
                            .noRippleClickable {
                                softwareKeyboardController?.hide()
                            },
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            )
        else
            SearchTopAppBar(
                onQueryChanged = onSearchQueryChanged,
                onCloseClicked = { state.value = AppbarState.Default }
            )
    }

}


@Composable
fun rememberToolbarState(): MutableState<AppbarState> {
    return remember { mutableStateOf(AppbarState.Default) }
}

@Composable
fun DefaultTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    icon: (@Composable () -> Unit)? = null,
) {
    TopAppBar {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp, 0.dp, 0.dp, 0.dp)
                        .noRippleClickable {
                            onBackClick()
                        },
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                    contentDescription = "back"
                )

                Text(text = title, style = Typography.h1)
            }


            icon?.invoke()
        }
    }
}

@Composable
fun SearchTopAppBar(
    onQueryChanged: (query: String) -> Unit,
    onCloseClicked: () -> Unit
) {
    val rememberSearchState = remember {
        mutableStateOf("")
    }
    TopAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextField(
                modifier = Modifier.fillMaxWidth(.9f),
                value = rememberSearchState.value,
                onValueChange = {
                    rememberSearchState.value = it
                    onQueryChanged(it)
                },
                label = { Text(text = "Search for titles...") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                },
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onQueryChanged(rememberSearchState.value)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .noRippleClickable {
                        onCloseClicked()
                    },
                imageVector = Icons.Filled.Close,
                contentDescription = "Search",
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
fun PreviewToolbarWithSearch() {
    ToolbarWithSearch(
        "Test",
        onBackClick = {  },
        onSearchQueryChanged = {  },
    )
}

@Preview
@Composable
fun PreviewDefaultToolbar() {
    DefaultTopAppBar(title = "Test", onBackClick = {  })
}