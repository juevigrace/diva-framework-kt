package io.github.juevigrace.diva.ui.window

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

internal actual object WindowUtilsImpl : WindowUtilsBase() {
    override val orientation: ScreenOrientation
        @Composable
        get() = calculateScreenOrientation()

    @Composable
    actual override fun calculateScreenOrientation(): ScreenOrientation {
        val winInf = LocalConfiguration.current
        return if (winInf.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ScreenOrientation.Portrait
        } else {
            ScreenOrientation.Landscape
        }
    }
}
