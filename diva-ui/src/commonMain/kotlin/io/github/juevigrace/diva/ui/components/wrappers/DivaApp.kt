package io.github.juevigrace.diva.ui.components.wrappers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import io.github.juevigrace.diva.ui.components.theme.DivaTheme
import io.github.juevigrace.diva.ui.theme.DivaThemeConfig

@Composable
fun DivaApp(
    vararg providers: ProvidedValue<*> = emptyArray(),
    themeConfig: DivaThemeConfig = DivaThemeConfig(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(*providers) {
        DivaTheme(
            config = themeConfig,
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    }
}
