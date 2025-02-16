package ru.chads.locket.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.chads.core_ui.theme.LocketTheme
import ru.chads.feature_camera_preview.ui.CameraPreviewScreen
import ru.chads.feature_editor.ui.LocketEditorScreen
import ru.chads.feature_editor.viewmodel.LocketEditorViewModelFactory
import ru.chads.feature_feed.ui.LocketFeedScreen
import ru.chads.locket.di.components.DaggerLocketFeedComponent
import ru.chads.navigation.LocketDestinations
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
                val navController: NavHostController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = LocketDestinations.LocketFeed.route
                ) {
                    composable(LocketDestinations.LocketFeed.route) {
                        LocketFeedScreen(
                            viewModel = viewModel(factory = viewModelFactory),
                            navController = navController,
                            snackbarHostState = snackbarHostState,
                        )
                    }
                    composable(LocketDestinations.LocketCreator.route) {
                        CameraPreviewScreen(
                            navController = navController,
                            snackbarHostState = snackbarHostState
                        )
                    }
                    composable(
                        "${LocketDestinations.LocketEditor.route}/{imageUri}",
                        arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val imageUri = Uri.parse(backStackEntry.arguments?.getString("imageUri"))
                        val factory = LocketEditorViewModelFactory(imageUri, viewModelFactory)
                        LocketEditorScreen(
                            navController = navController,
                            viewModel = viewModel(factory = factory)
                        )
                    }
                }
            }
        }
    }
}