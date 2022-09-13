package com.benyaamin.privateapp.ui.screens.note

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.lifecycle.viewmodel.compose.viewModel
import com.benyaamin.privateapp.R
import com.benyaamin.privateapp.extensions.noRippleClickable
import com.benyaamin.privateapp.models.Note
import com.benyaamin.privateapp.ui.components.AppbarState
import com.benyaamin.privateapp.ui.components.ConfirmDialog
import com.benyaamin.privateapp.ui.components.Fab
import com.benyaamin.privateapp.ui.screens.destinations.NewNoteScreenDestination
import com.benyaamin.privateapp.ui.theme.Typography
import com.benyaamin.privateapp.ui.theme.colorPrimary


@Destination
@Composable
fun NoteScreen(
    navigator: DestinationsNavigator,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val rememberAppbarState = remember {
        mutableStateOf(AppbarState.Default)
    }

    Scaffold(
        topBar = {
            if (rememberAppbarState.value == AppbarState.Default) {
                NoteDefaultTopAppBar {
                    rememberAppbarState.value = AppbarState.Search
                }
            } else {
                NoteSearchTopAppBar(viewModel) {
                    rememberAppbarState.value = AppbarState.Default
                }
            }
        },
        floatingActionButton = {
            Fab("Add Note", Icons.Filled.Add) {
                navigator.navigate(NewNoteScreenDestination)
            }
        },
        content = {
            NotesList(viewModel)
        }
    )
}

@Composable
fun NoteDefaultTopAppBar(
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
fun NoteSearchTopAppBar(
    viewModel: NotesViewModel,
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
fun NotesList(
    viewModel: NotesViewModel
) {
    val listState by viewModel.notesFlow.collectAsState()

    LazyColumn(contentPadding = PaddingValues(8.dp), content = {
        items(listState.size) { index ->
            NoteListItem(listState[index], viewModel)
        }
    })
}

@Composable
fun NoteListItem(
    note: Note = Note(
        "test",
        "test content",
        false
    ),
    viewModel: NotesViewModel
) {
    val confirmDialogState = remember {
        mutableStateOf(false)
    }

    ConfirmDialog(
        dialogState = confirmDialogState.value,
        title = "Delete ${note.title}",
        onDismiss = {
            if (it) {
                viewModel.deleteNote(note)
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

    }
}
