package com.benyaamin.privateapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import com.benyaamin.privateapp.ui.screens.NavGraphs
import com.benyaamin.privateapp.ui.theme.PrivateTheme
import com.benyaamin.privateapp.ui.theme.colorPrimary
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrivateTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(colorPrimary, true)
                val animationRemember = rememberAnimatedNavHostEngine()
                DestinationsNavHost(navGraph = NavGraphs.root, engine = animationRemember)
            }
        }
    }
}