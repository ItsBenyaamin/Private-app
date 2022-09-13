package com.benyaamin.privateapp.ui.screens.note

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benyaamin.privateapp.extensions.noRippleClickable
import com.benyaamin.privateapp.models.Note
import com.benyaamin.privateapp.ui.components.DefaultTopAppBar
import com.benyaamin.privateapp.ui.screens.destinations.NoteScreenDestination
import com.benyaamin.privateapp.ui.theme.PrivateTheme
import com.benyaamin.privateapp.ui.theme.colorPrimary
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun NewNoteScreen(
    navigator: DestinationsNavigator,
    viewModel: NewNoteViewModel = hiltViewModel()
) {
    val backHandler = LocalOnBackPressedDispatcherOwner.current
    var titleState by remember {
        mutableStateOf("")
    }
    var contentState by remember {
        mutableStateOf("")
    }
    var favoriteState by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = "New Note",
                onBackClick = { backHandler?.onBackPressedDispatcher?.onBackPressed() },
                icon = {
                    Row {
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .noRippleClickable {
                                    favoriteState = !favoriteState
                                },
                            imageVector = if (favoriteState)
                                Icons.Filled.Favorite
                            else
                                Icons.Filled.FavoriteBorder,
                            contentDescription = "Done",
                            tint = Color.White
                        )
                        
                        Icon(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .noRippleClickable {
                                    val note = Note(
                                        titleState,
                                        contentState,
                                        favoriteState
                                    )
                                    viewModel.newNote(note)
                                    navigator.navigate(NoteScreenDestination)
                                },
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(vertical = 4.dp),
                value = titleState,
                onValueChange = { titleState = it },
                singleLine = true,
                maxLines = 1,
                placeholder = { Text(text = "Enter the title...") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = colorPrimary,
                    unfocusedIndicatorColor = Color.Black,
                    backgroundColor = Color.White
                )
            )
            
            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            TextField(
                modifier = Modifier
                    .fillMaxSize(),
                value = contentState,
                onValueChange = { contentState = it },
                placeholder = { Text(text = "Enter the Content...") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = colorPrimary,
                    unfocusedIndicatorColor = Color.Black,
                    backgroundColor = Color.White
                )
            )
        }
    }
}
