package io.github.juevigrace.diva.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

data class ThemeScheme(
    val light: ColorScheme = lightColorScheme(),
    val dark: ColorScheme = darkColorScheme(),
)

data class DivaThemeConfig(
    val themeScheme: ThemeScheme = ThemeScheme(),
    val typography: Typography = Typography(),
    val shapes: Shapes = Shapes(),
)
