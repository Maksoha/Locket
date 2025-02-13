package ru.chads.locket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.collections.immutable.persistentListOf
import ru.chads.locket.screens.LockedFeedScreen
import ru.chads.locket.screens.components.LocketSnippetModel
import ru.chads.locket.ui.theme.LocketTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LocketTheme {
                NavHost(
                    navController = rememberNavController(),
                    startDestination = LocketScreens.LockedFeed.route
                ) {
                    composable(LocketScreens.LockedFeed.route) {
                        LockedFeedScreen(
                            isLoading = true,
                            snippets = persistentListOf(
                                LocketSnippetModel(
                                    imageUrl = "",
                                    userAvatarUrl = "",
                                    username = "Username",
                                    description = "Cупер мега описание - комментарий парапупу это mvp потом меня можно занести на картинку с блюром и тенями" +
                                            "Cупер мега описание - комментарий парапупу это mvp потом меня можно занести на картинку с блюром и тенями"
                                ),
                                LocketSnippetModel(
                                    imageUrl = "",
                                    userAvatarUrl = "",
                                    username = "Username",
                                    description = "Cупер мега описание - комментарий парапупу это mvp потом меня можно занести на картинку с блюром и тенями"
                                ),
                                LocketSnippetModel(
                                    imageUrl = "",
                                    userAvatarUrl = "",
                                    username = "Username",
                                    description = "Cупер мега описание - комментарий парапупу это mvp потом меня можно занести на картинку с блюром и тенями"
                                ),
                                LocketSnippetModel(
                                    imageUrl = "",
                                    userAvatarUrl = "",
                                    username = "Username",
                                    description = "Cупер мега описание - комментарий парапупу это mvp потом меня можно занести на картинку с блюром и тенями"
                                ),
                                LocketSnippetModel(
                                    imageUrl = "",
                                    userAvatarUrl = "",
                                    username = "Username",
                                    description = "Cупер мега описание - комментарий парапупу это mvp потом меня можно занести на картинку с блюром и тенями"
                                ),
                            )
                        )
                    }
                }
            }
        }
    }
}