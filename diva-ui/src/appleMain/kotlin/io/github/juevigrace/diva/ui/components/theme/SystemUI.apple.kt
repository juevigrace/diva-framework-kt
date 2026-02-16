package io.github.juevigrace.diva.ui.components.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import io.github.juevigrace.diva.ui.theme.ThemeScheme
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

@Composable
actual fun ConfigureSystemUI(
    isDark: Boolean,
    themeScheme: ThemeScheme
) {
    LaunchedEffect(isDark) {
        UIApplication.sharedApplication.setStatusBarStyle(
            if (isDark) UIStatusBarStyleDarkContent else UIStatusBarStyleLightContent
        )
    }
}