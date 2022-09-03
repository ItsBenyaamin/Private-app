@file:OptIn(ExperimentalComposeUiApi::class)

package com.benyaamin.privateapp.ui.screens.password

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.benyaamin.privateapp.R
import com.benyaamin.privateapp.models.Password
import com.benyaamin.privateapp.ui.components.SelectedOption
import com.benyaamin.privateapp.ui.components.TwoOptionSelectComponent
import com.benyaamin.privateapp.ui.theme.Background
import com.benyaamin.privateapp.ui.theme.colorPrimary


@Composable
fun AddNewPasswordDialog(
    dialogState: Boolean,
    viewModel: PasswordViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    if (dialogState) {
        val context = LocalContext.current
        val keyboardController = LocalSoftwareKeyboardController.current

        var titleState by remember {
            mutableStateOf("")
        }

        var usernameState by remember {
            mutableStateOf("")
        }

        var passwordState by remember {
            mutableStateOf("")
        }

        var typeState by remember {
            mutableStateOf(-1)
        }

        Dialog(
            onDismissRequest = { },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Background, RoundedCornerShape(8.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Add New Password", color = Color.Black, fontSize =18.sp)
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = titleState,
                    onValueChange = { titleState = it },
                    placeholder = { Text(text = "Enter Title") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = "Enter Title"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = usernameState,
                    onValueChange = { usernameState = it },
                    placeholder = { Text(text = "Enter Username") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AccountBox,
                            contentDescription = "Enter Username"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = passwordState,
                    onValueChange = { passwordState = it },
                    placeholder = { Text(text = "Enter Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Enter Password"
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Password Type:")

                    Spacer(modifier = Modifier.padding(horizontal = 16.dp))

                    TwoOptionSelectComponent(
                        leftSidePainterId = R.drawable.ic_phone,
                        rightSidePainterId = R.drawable.ic_world,
                        leftSideIconTint = Color.Black,
                        rightSideIconTint = Color.Black,
                        tintOnSelection = colorPrimary
                    ) {
                        typeState = if (it == SelectedOption.LeftSide)
                            0
                        else
                            1
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            if (usernameState.isEmpty()) {
                                showToast(context, "Please enter a Username!")
                            }else if (passwordState.isEmpty()) {
                                showToast(context, "Please enter a Password!")
                            }else if (typeState == -1) {
                                showToast(context, "Please select password type!")
                            }else {
                                viewModel.addNewPassword(Password(
                                    titleState,
                                    usernameState,
                                    passwordState,
                                    typeState
                                ))
                                onDismiss()
                            }
                        },
                        modifier = Modifier.width(100.dp)
                    ) {
                        Text(text = "OK")
                    }

                    TextButton(
                        onClick = {
                            onDismiss()
                        },
                        modifier = Modifier.width(100.dp)
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
}

private fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun PreviewAddNewPasswordDialog() {
    AddNewPasswordDialog(true) {}
}