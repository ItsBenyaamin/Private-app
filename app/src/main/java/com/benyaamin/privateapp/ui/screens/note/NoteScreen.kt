package com.benyaamin.privateapp.ui.screens.note

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.benyaamin.privateapp.extensions.noRippleClickable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.benyaamin.privateapp.models.Note
import com.benyaamin.privateapp.ui.components.ConfirmDialog
import com.benyaamin.privateapp.ui.components.Fab
import com.benyaamin.privateapp.ui.components.ToolbarWithSearch
import com.benyaamin.privateapp.ui.screens.destinations.EditNoteScreenDestination
import com.benyaamin.privateapp.ui.screens.destinations.NewNoteScreenDestination
import com.benyaamin.privateapp.ui.theme.Typography
import com.benyaamin.privateapp.ui.theme.colorPrimary
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient


@Destination
@Composable
fun NoteScreen(
    navigator: DestinationsNavigator,
    newResultRecipient: ResultRecipient<NewNoteScreenDestination, Boolean>,
    editResultRecipient: ResultRecipient<EditNoteScreenDestination, Boolean>,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val backHandler = LocalOnBackPressedDispatcherOwner.current
    
    newResultRecipient.onNavResult {
        when(it) {
            NavResult.Canceled -> {}
            is NavResult.Value -> { viewModel.reloadList() }
        }
    }
    editResultRecipient.onNavResult {
        when(it) {
            NavResult.Canceled -> {}
            is NavResult.Value -> { viewModel.reloadList() }
        }
    }
    
    Scaffold(
        topBar = {
            ToolbarWithSearch(
                title = "Notes",
                onBackClick = { backHandler?.onBackPressedDispatcher?.onBackPressed() },
                onSearchQueryChanged = {
                    viewModel.searchWith(it)
                }
            )
        },
        floatingActionButton = {
            Fab("Add Note", Icons.Filled.Add) {
                navigator.navigate(NewNoteScreenDestination)
            }
        },
        content = {
            NotesList(navigator, viewModel)
        }
    )
}


@Composable
fun NotesList(
    navigator: DestinationsNavigator,
    viewModel: NotesViewModel
) {
    val listState by viewModel.notesFlow.collectAsState()

    LazyColumn(contentPadding = PaddingValues(8.dp), content = {
        items(listState.size) { index ->
            NoteListItem(
                listState[index],
                navigator,
                viewModel
            )
        }
    })
}

@Composable
fun NoteListItem(
    note: Note = Note(
        title = "test",
        content = "test content",
        isFavorite = false
    ),
    navigator: DestinationsNavigator,
    viewModel: NotesViewModel
) {
    var confirmDialogState by remember {
        mutableStateOf(false)
    }

    ConfirmDialog(
        dialogState = confirmDialogState,
        title = "Delete ${note.title}",
        onDismiss = {
            if (it) {
                viewModel.deleteNote(note)
            }
            confirmDialogState = false
        }
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp),
        backgroundColor = colorPrimary,
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier
            .padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(.8f),
                    text = "Title: ${note.title}",
                    color = Color.White,
                    style = Typography.h2
                )

                Row {
                    Icon(
                        modifier = Modifier.noRippleClickable {
                            navigator.navigate(EditNoteScreenDestination(note))
                        },
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Note",
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                    Icon(
                        modifier = Modifier.noRippleClickable {
                            confirmDialogState = !confirmDialogState
                        },
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Note",
                        tint = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            
            Text(text = "${note.content.take(200)}...", color = Color.White, style = Typography.h3)
        }
    }
}
