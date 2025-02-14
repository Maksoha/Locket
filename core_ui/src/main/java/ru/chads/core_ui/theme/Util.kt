package ru.chads.core_ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Day", group = "uiMode", showBackground = true, backgroundColor = 0xFFF3F4F6)
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES, group = "uiMode", showBackground = true, backgroundColor = 0xFF000000)
annotation class DayNightPreview