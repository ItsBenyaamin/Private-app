package com.benyaamin.privateapp.ui.screens.password

import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.benyaamin.privateapp.R
import com.benyaamin.privateapp.extensions.noRippleClickable
import com.benyaamin.privateapp.models.Password
import com.benyaamin.privateapp.ui.components.ConfirmDialog
import com.benyaamin.privateapp.ui.theme.Typography
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.benyaamin.privateapp.ui.theme.colorPrimary

@Destination
@Composable
fun PasswordScreen(
    navigator: DestinationsNavigator,
    viewModel: PasswordViewModel = hiltViewModel()
) {
    val rememberAppbarState = remember {
        mutableStateOf(PasswordAppbarState.Default)
    }

    Scaffold(
        topBar = {
            if (rememberAppbarState.value == PasswordAppbarState.Default) {
                PasswordDefaultTopAppBar {
                    rememberAppbarState.value = PasswordAppbarState.Search
                }
            }else {
                PasswordSearchTopAppBar(viewModel) {
                    rememberAppbarState.value = PasswordAppbarState.Default
                }
            }
        },
        floatingActionButton = { Fab() },
        content = {
            PasswordsList(viewModel)
        }
    )
}

@Composable
fun PasswordDefaultTopAppBar(
    onSearchClicked: () -> Unit
) {
    val onBackPressedDispatcherOwner = LocalOnBackPressedDispatcherOwner.current
    TopAppBar {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row() {
                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp, 0.dp, 0.dp, 0.dp)
                        .noRippleClickable {
                            onBackPressedDispatcherOwner?.onBackPressedDispatcher?.onBackPressed()
                        },
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                    contentDescription = "back"
                )

                Text(text = "Passwords", style = Typography.h1)
            }

            Icon(
                modifier = Modifier
                    .padding(start = 8.dp, 0.dp, 16.dp, 0.dp)
                    .noRippleClickable {
                        onSearchClicked()
                    },
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
        }
    }
}

@Composable
fun PasswordSearchTopAppBar(
    viewModel: PasswordViewModel,
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
                    viewModel.searchWith(it)
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
                        viewModel.searchWith(rememberSearchState.value)
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                )
            )

            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .noRippleClickable {
                        viewModel.reloadList()
                        onCloseClicked()
                    },
                imageVector = Icons.Filled.Close,
                contentDescription = "Search",
                tint = Color.White
            )
        }
    }
}

@Composable
fun Fab() {
    val dialogState = remember {
        mutableStateOf(false)
    }
    AddNewPasswordDialog(dialogState.value) {
        dialogState.value = false
    }
    FloatingActionButton(onClick = { dialogState.value = true }, backgroundColor = colorPrimary) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add Password",
            tint = Color.White
        )
    }
}

@Composable
fun PasswordsList(
    viewModel: PasswordViewModel
) {
    val listState by viewModel.passwordFlow.collectAsState()

    LazyColumn(contentPadding = PaddingValues(8.dp), content = {
        items(listState.size) { index ->
            PasswordListItem(listState[index], viewModel)
        }
    })
}

@Composable
fun PasswordListItem(
    password: Password = Password(
        "Something",
        "Benyamin",
        "Eskandari",
        0
    ),
    viewModel: PasswordViewModel
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    var editDialogState by remember {
        mutableStateOf(false)
    }

    EditPasswordDialog(editDialogState, viewModel, password) {
        editDialogState = false
    }

    val confirmDialogState = remember {
        mutableStateOf(false)
    }

    ConfirmDialog(
        dialogState = confirmDialogState.value,
        title = "Delete ${password.title}",
        onDismiss = {
            if (it) {
                viewModel.deletePassword(password)
            }
            confirmDialogState.value = false
        }
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(vertical = 4.dp),
        backgroundColor = colorPrimary,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp
    ) {
        val constraintSet = ConstraintSet {
            val typeIcon = createRefFor("typeIcon")
            val info = createRefFor("info")
            val actions = createRefFor("actions")

            constrain(typeIcon) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }

            constrain(info) {
                start.linkTo(typeIcon.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(actions.start)
                width = Dimension.fillToConstraints
            }

            constrain(actions) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        }

        ConstraintLayout(constraintSet = constraintSet) {
            Box(
                modifier = Modifier
                    .layoutId("typeIcon")
                    .padding(8.dp)
                    .width(60.dp)
                    .wrapContentHeight(), contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        id = if (password.type == 0) R.drawable.ic_phone else R.drawable.ic_world
                    ),
                    tint = Color.White,
                    contentDescription = if (password.type == 0) "App Password" else "Website Password"
                )
            }

            Column(
                modifier = Modifier
                    .layoutId("info")
                    .padding(horizontal = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_title_24),
                        tint = Color.White,
                        contentDescription = "Password Title"
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                    Text(text = password.title, color = Color.White)
                }

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                Row(
                    modifier = Modifier
                        .noRippleClickable {
                            clipboardManager.setText(AnnotatedString(password.username))
                            Toast.makeText(
                                context,
                                "Username copied to clipboard.",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_user),
                        tint = Color.White,
                        contentDescription = "Username"
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                    Text(text = password.username, color = Color.White)
                }

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                Row(
                    modifier = Modifier
                        .noRippleClickable {
                            clipboardManager.setText(AnnotatedString(password.username))
                            Toast.makeText(
                                context,
                                "Password copied to clipboard.",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_pass),
                        tint = Color.White,
                        contentDescription = "Password"
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                    Text(
                        text = password.password,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                modifier = Modifier
                    .layoutId("actions")
                    .padding(horizontal = 16.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .noRippleClickable {
                            editDialogState = true
                        },
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit the Password",
                    tint = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Icon(
                    modifier = Modifier
                        .noRippleClickable {
                            confirmDialogState.value = true
                        },
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete the Password",
                    tint = Color.White
                )
            }
        }
    }
}