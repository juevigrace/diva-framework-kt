package io.github.juevigrace.diva.ui.theme

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.core.view.WindowCompat

@androidx.compose.runtime.Composable
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
