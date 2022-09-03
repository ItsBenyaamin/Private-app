package com.benyaamin.privateapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.benyaamin.privateapp.ui.theme.Background
import com.benyaamin.privateapp.ui.theme.Typography
import com.benyaamin.privateapp.ui.theme.colorPrimary


@Composable
fun ConfirmDialog(
    dialogState: Boolean,
    title: String,
    onDismiss: (result: Boolean) -> Unit
) {
    if (dialogState) {
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
                    Text(text = title, style = Typography.h2, color = colorPrimary)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Are you sure?",
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            onDismiss(true)
                        },
                        modifier = Modifier.width(100.dp)
                    ) {
                        Text(text = "Yes")
                    }

                    TextButton(
                        onClick = {
                            onDismiss(false)
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

@Preview
@Composable
fun PreviewAddNewPasswordDialog() {
    ConfirmDialog(true, "Delete Folan") {

    }
}