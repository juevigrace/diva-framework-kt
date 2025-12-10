package io.github.juevigrace.diva.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DivaTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    config: DivaThemeConfig = DivaThemeConfig(),
    systemUiConfig: @Composable (isDark: Boolean, themeScheme: ThemeScheme) -> Unit = ::ConfigureSystemUI,
    content: @Composable () -> Unit
) {
    systemUiConfig(isDark, config.themeScheme)
    MaterialTheme(
        colorScheme = if (isDark) config.themeScheme.dark else config.themeScheme.light,
        typography = config.typography,
        shapes = config.shapes,
        content = content
    )
}
