package ru.chads.feature_editor.ui

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import ru.chads.core_ui.theme.DayNightPreview
import ru.chads.core_ui.theme.LocketTheme
import ru.chads.feature_editor.viewmodel.LocketEditorViewModel
import ru.chads.navigation.NavCommand

@Composable
fun LocketEditorScreen(
    navController: NavController,
    viewModel: LocketEditorViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navCommand.collectLatest { navCommand ->
            when (navCommand) {
                is NavCommand.RouterCommand -> navController.navigate(navCommand.route)
                NavCommand.GoBack -> navController.popBackStack()
            }
        }
    }

    LocketEditorContent(
        imageUri = state.imageUri,
        description = state.description,
        hasPhotoSubmitted = state.hasPhotoSubmitted,
        onDescriptionChanged = viewModel::onDescriptionChanged,
        onPublishClick = viewModel::onPublishClick,
        onBackClick = viewModel::onBackClick,
        modifier = modifier
    )
}

@Composable
fun LocketEditorContent(
    imageUri: Uri,
    description: String,
    hasPhotoSubmitted: Boolean,
    modifier: Modifier = Modifier,
    onDescriptionChanged: (String) -> Unit,
    onPublishClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopBar(onBackClick = onBackClick) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Fab(
                hasPhotoSubmitted = hasPhotoSubmitted,
                onClick = onPublishClick
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .offset(y = (-48).dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(24.dp))
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    value = description,
                    placeholder = { Text("Description") },
                    label = { Text("Description") },
                    onValueChange = onDescriptionChanged,
                    singleLine = false,
                )
            }
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier
            .statusBarsPadding()
            .fillMaxWidth()
    ) {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }
}

@Composable
private fun Fab(
    hasPhotoSubmitted: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(onClick = onClick, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = when {
                    hasPhotoSubmitted -> "Publishing"
                    else -> "Publish"
                }
            )
            when {
                hasPhotoSubmitted -> CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
                else -> Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
@DayNightPreview
private fun Preview() {
    LocketTheme {
        LocketEditorContent(
            imageUri = Uri.EMPTY,
            hasPhotoSubmitted = true,
            description = "",
            onDescriptionChanged = {},
            onBackClick = {},
            onPublishClick = {}
        )
    }
}