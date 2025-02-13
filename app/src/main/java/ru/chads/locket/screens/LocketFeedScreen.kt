package ru.chads.locket.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import ru.chads.locket.screens.components.LocketSnippet
import ru.chads.locket.screens.components.LocketSnippetModel
import ru.chads.locket.screens.components.LocketSnippetSkeleton
import ru.chads.locket.ui.theme.DayNightPreview
import ru.chads.locket.ui.theme.LocketTheme


@Composable
fun LockedFeedScreen(isLoading: Boolean, snippets: ImmutableList<LocketSnippetModel>) {
    Scaffold(
        floatingActionButton = {
            Icon(
                modifier = Modifier.size(FabSize),
                imageVector = Icons.Filled.AddCircle,
                contentDescription = null
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        when {
            isLoading -> LoadingContent(paddingValues)
            else -> LoadedContent(snippets = snippets, paddingValues = paddingValues)
        }
    }
}

@Composable
private fun LoadingContent(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        LocketSnippetSkeleton()
    }
}

@Composable
private fun LoadedContent(
    snippets: ImmutableList<LocketSnippetModel>,
    paddingValues: PaddingValues
) {
    val pagerState = rememberPagerState { snippets.size }
    VerticalPager(
        state = pagerState,
        contentPadding = paddingValues
    ) { page: Int ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LocketSnippet(locketSnippet = snippets[page], modifier = Modifier.offset(y = -FabSize))
        }
    }
}

private val FabSize = 64.dp

@DayNightPreview
@Composable
private fun Preview() {
    LocketTheme {
        LockedFeedScreen(
            isLoading = true,
            snippets = persistentListOf(
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