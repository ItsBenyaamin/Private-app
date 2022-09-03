package com.benyaamin.privateapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.benyaamin.privateapp.R
import com.benyaamin.privateapp.extensions.noRippleClickable

enum class SelectedOption {
    LeftSide,
    RightSide,
    None
}

@Composable
fun TwoOptionSelectComponent(
    leftSidePainterId: Int,
    rightSidePainterId: Int,
    leftSideIconTint: Color = Color.Black,
    rightSideIconTint: Color = Color.Black,
    tintOnSelection: Color = Color.Blue,
    backgroundColor: Color = Color.White,
    onSelectChange: (selectedOption: SelectedOption) -> Unit
) {
    var rememberOptionState by remember {
        mutableStateOf(SelectedOption.None)
    }


    Card(
        modifier = Modifier.background(backgroundColor, RoundedCornerShape(4.dp)),
        elevation = 4.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp), contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .noRippleClickable {
                            rememberOptionState = SelectedOption.LeftSide
                            onSelectChange(SelectedOption.LeftSide)
                        },
                    painter = painterResource(id = leftSidePainterId),
                    tint = if (rememberOptionState == SelectedOption.LeftSide) tintOnSelection else leftSideIconTint,
                    contentDescription = "Password Type Application"
                )
            }

            Spacer(modifier = Modifier.padding(horizontal = 8.dp))

            Box(
                modifier = Modifier
                    .size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .noRippleClickable {
                            rememberOptionState = SelectedOption.RightSide
                            onSelectChange(SelectedOption.RightSide)
                        },
                    painter = painterResource(id = rightSidePainterId),
                    tint = if (rememberOptionState == SelectedOption.RightSide) tintOnSelection else rightSideIconTint,
                    contentDescription = "Password Type Website"
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTwoOptionSelectComponent() {
    TwoOptionSelectComponent(
        leftSidePainterId = R.drawable.ic_phone,
        rightSidePainterId = R.drawable.ic_world
    ) {

    }
}