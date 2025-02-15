package ru.chads.feature_feed.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.collectLatest
import ru.chads.core_ui.theme.DayNightPreview
import ru.chads.core_ui.theme.LocketTheme
import ru.chads.data.model.LocketInfo
import ru.chads.feature_feed.viewmodel.LocketFeedViewModel
import ru.chads.feature_feed.viewmodel.State
import ru.chads.feature_feed.ui.components.LocketSnippet
import ru.chads.feature_feed.ui.components.LocketSnippetSkeleton

@Composable
fun LocketFeedScreen(
    viewModel: LocketFeedViewModel,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val state by viewModel.lockedFeedState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navCommand.collectLatest { command ->
            navController.navigate(command.route)
        }
    }

    Scaffold(
        floatingActionButton = { FabButton(onAddClick = viewModel::onAddLocketClick) },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        when (state) {
            is State.Loaded -> {
                val locketSnippets = requireNotNull(state as? State.Loaded).locketSnippets
                LoadedContent(
                    snippets = locketSnippets,
                    paddingValues = paddingValues,
                    onShouldLoadMoreLockets = viewModel::checkAndFetchMoreLockets
                )
            }

            is State.Loading -> LoadingContent()
            is State.Error -> {
                val message = requireNotNull(state as? State.Error).message
                ErrorContent(message = message, onRetryClick = viewModel::onRetryClick)
            }
        }
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LocketSnippetSkeleton()
    }
}

@Composable
private fun ErrorContent(modifier: Modifier = Modifier, message: String, onRetryClick: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = message)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetryClick) {
                Text("Повторить")
            }
        }
    }
}

@Composable
private fun LoadedContent(
    snippets: ImmutableList<LocketInfo>,
    paddingValues: PaddingValues = PaddingValues(),
    onShouldLoadMoreLockets: (snippetIndex: Int, totalSnippets: Int) -> Unit,
) {
    val pagerState = rememberPagerState { snippets.size }
    VerticalPager(
        state = pagerState,
        contentPadding = paddingValues
    ) { page: Int ->
        onShouldLoadMoreLockets(page, pagerState.pageCount)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LocketSnippet(locketSnippet = snippets[page], modifier = Modifier.offset(y = -FabSize))
        }
    }
}

@Composable
private fun FabButton(onAddClick: () -> Unit) {
    IconButton(
        modifier = Modifier.size(FabSize),
        onClick = onAddClick
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = Icons.Filled.AddCircle,
            contentDescription = null
        )
    }
}

private val FabSize = 48.dp

@DayNightPreview
@Composable
private fun PreviewLoadedContent() {
    LocketTheme {
        LoadedContent(
            snippets = persistentListOf(
                LocketInfo(
                    imageUrl = "",
                    userAvatarUrl = "",
                    username = "username",
                    description = "description"
                ),
            ),
            onShouldLoadMoreLockets = { _, _ -> }
        )
    }
}

@DayNightPreview
@Composable
private fun PreviewLoadingContent() {
    LocketTheme {
        LoadingContent()
    }
}

@DayNightPreview
@Composable
private fun PreviewErrorContent() {
    LocketTheme {
        ErrorContent(message = "Что-то пошло не так", onRetryClick = {})
    }
}
