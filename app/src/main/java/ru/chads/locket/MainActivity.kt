package ru.chads.locket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.chads.core_ui.theme.LocketTheme
import ru.chads.feature_feed.LocketFeedScreen
import ru.chads.locket.di.components.DaggerLocketFeedComponent
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        DaggerLocketFeedComponent.builder()
            .build()
            .inject(this)

        setContent {
            LocketTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                NavHost(
                    navController = rememberNavController(),
                    startDestination = LocketScreens.LockedFeed.route
                ) {
                    composable(LocketScreens.LockedFeed.route) {
                        LocketFeedScreen(
                            viewModel = viewModel(factory = viewModelFactory),
                            snackbarHostState = snackbarHostState,
                        )
                    }
                }
            }
        }
    }
}