package com.benyaamin.privateapp.ui.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.benyaamin.privateapp.ui.theme.colorPrimary

@Composable
fun Fab(
    title: String,
    icon: ImageVector,
    onFabClick: () -> Unit
) {
    FloatingActionButton(onClick = { onFabClick() }, backgroundColor = colorPrimary) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.White
        )
    }
}