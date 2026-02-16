package io.github.juevigrace.diva.ui.components.theme

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import io.github.juevigrace.diva.ui.theme.ThemeScheme

@Composable
actual fun ConfigureSystemUI(
    isDark: Boolean,
    themeScheme: ThemeScheme
) {
    val activity: Activity? = LocalActivity.current
    activity?.let { a ->
        WindowCompat.getInsetsController(a.window, a.window.decorView)
            .isAppearanceLightStatusBars = !isDark
    }
}
