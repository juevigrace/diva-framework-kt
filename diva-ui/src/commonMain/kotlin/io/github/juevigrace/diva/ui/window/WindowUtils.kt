package io.github.juevigrace.diva.ui.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalWindowUtils: ProvidableCompositionLocal<WindowUtils> = compositionLocalOf { WindowUtils() }

interface WindowUtils {
    @get:Composable
    val width: Dp
        get() = Dp.Unspecified

    @get:Composable
    val height: Dp
        get() = Dp.Unspecified

    @get:Composable
    val orientation: ScreenOrientation
        get() = ScreenOrientation.Portrait

    @get:Composable
    val size: ScreenSize
        get() = ScreenSize.Small

    @get:Composable
    val isPortrait: Boolean
        get() = orientation == ScreenOrientation.Portrait

    @get:Composable
    val isLandscape: Boolean
        get() = orientation == ScreenOrientation.Landscape

    companion object {
        operator fun invoke(): WindowUtils {
            return WindowUtilsImpl
        }
    }
}

internal abstract class WindowUtilsBase : WindowUtils {
    override val width: Dp
        @Composable
        get() = calculateScreenWidth()

    override val height: Dp
        @Composable
        get() = calculateScreenHeight()

    override val size: ScreenSize
        @Composable
        get() = calculateScreenSize()

    @Composable
    private fun calculateScreenWidth(): Dp {
        val winInf = LocalWindowInfo.current
        val density = LocalDensity.current
        val w = with(density) {
            winInf.containerSize.width.toDp()
        }
        return w
    }

    @Composable
    private fun calculateScreenHeight(): Dp {
        val winInf = LocalWindowInfo.current
        val density = LocalDensity.current
        val h = with(density) {
            winInf.containerSize.height.toDp()
        }
        return h
    }

    @Composable
    private fun calculateScreenSize(): ScreenSize {
        return when (orientation) {
            ScreenOrientation.Portrait -> {
                when {
                    width < 600.dp && height < 900.dp -> ScreenSize.Small
                    width < 840.dp && height < 1200.dp -> ScreenSize.Medium
                    width < 1200.dp && height < 1500.dp -> ScreenSize.Large
                    else -> ScreenSize.XLarge
                }
            }
            ScreenOrientation.Landscape -> {
                when {
                    height < 600.dp && width < 900.dp -> ScreenSize.Small
                    height < 840.dp && width < 1200.dp -> ScreenSize.Medium
                    height < 1200.dp && width < 1500.dp -> ScreenSize.Large
                    else -> ScreenSize.XLarge
                }
            }
        }
    }

    @Composable
    protected abstract fun calculateScreenOrientation(): ScreenOrientation
}

internal expect object WindowUtilsImpl : WindowUtilsBase {
    @Composable
    override fun calculateScreenOrientation(): ScreenOrientation
}
