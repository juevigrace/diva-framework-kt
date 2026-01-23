package io.github.juevigrace.diva.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DivaTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    config: DivaThemeConfig = DivaThemeConfig(),
    systemUiConfig: @Composable () -> Unit = {
        ConfigureSystemUI(isDark, config.themeScheme)
    },
    content: @Composable () -> Unit
) {
    systemUiConfig()
    MaterialTheme(
        colorScheme = if (isDark) config.themeScheme.dark else config.themeScheme.light,
        typography = config.typography,
        shapes = config.shapes,
        content = content
    )
}
