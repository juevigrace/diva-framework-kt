package io.github.juevigrace.diva.ui.window

import androidx.compose.runtime.Composable

internal actual object WindowUtilsImpl : WindowUtilsBase() {
    override val orientation: ScreenOrientation
        @Composable
        get() = calculateScreenOrientation()

    @Composable
    actual override fun calculateScreenOrientation(): ScreenOrientation {
        return if (width < height) {
            ScreenOrientation.Portrait
        } else {
            ScreenOrientation.Landscape
        }
    }
}
