package com.example.danceclub.ui.utils

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Light theme", group = "compact", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark theme", group = "compact", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class PreviewLightDark