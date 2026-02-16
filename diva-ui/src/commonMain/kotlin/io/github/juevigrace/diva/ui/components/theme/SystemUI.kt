package io.github.juevigrace.diva.ui.components.theme

import androidx.compose.runtime.Composable
import io.github.juevigrace.diva.ui.theme.ThemeScheme

@Composable
expect fun ConfigureSystemUI(isDark: Boolean, themeScheme: ThemeScheme)
