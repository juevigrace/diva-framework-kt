package io.github.juevigrace.diva.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
@Stable
data class ThemeScheme(
    val light: ColorScheme = lightColorScheme(),
    val dark: ColorScheme = darkColorScheme(),
)

@Immutable
@Stable
data class DivaThemeConfig(
    val themeScheme: ThemeScheme = ThemeScheme(),
    val typography: Typography = Typography(),
    val shapes: Shapes = Shapes(),
)
