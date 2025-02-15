package ru.chads.feature_feed.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.chads.core_ui.shimmer.shimmer
import ru.chads.core_ui.theme.DayNightPreview
import ru.chads.core_ui.theme.LocketTheme
import ru.chads.data.model.LocketInfo

@Composable
fun LocketSnippet(locketSnippet: LocketInfo, modifier: Modifier = Modifier) =
    with(locketSnippet) {
        Column(modifier = modifier) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(24.dp)),
                model = imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Row(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(UserAvatarSize),
                    model = userAvatarUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                )
                Text(text = username, style = MaterialTheme.typography.titleMedium)
            }
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = DescriptionMaxLines,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }

@Composable
fun LocketSnippetSkeleton(modifier: Modifier = Modifier, skeletonColor: Color = Color.LightGray) {
    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(skeletonColor)
                .shimmer()
        )
        Row(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(UserAvatarSize)
                    .clip(CircleShape)
                    .background(skeletonColor)
                    .shimmer()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(skeletonColor)
                    .shimmer()
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(skeletonColor)
                .shimmer()
        )
    }
}

private const val DescriptionMaxLines = 3
private val UserAvatarSize = 24.dp

@DayNightPreview
@Composable
private fun Preview() {
    LocketTheme {
        Surface {
            LocketSnippet(
                locketSnippet = LocketInfo(
                    imageUrl = "",
                    userAvatarUrl = "",
                    username = "Username",
                    description = "Cупер мега описание - комментарий парапупу это mvp потом меня можно занести на картинку с блюром и тенями"
                )
            )
        }
    }
}