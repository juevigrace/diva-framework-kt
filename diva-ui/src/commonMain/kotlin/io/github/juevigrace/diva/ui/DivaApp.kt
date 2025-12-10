package io.github.juevigrace.diva.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import io.github.juevigrace.diva.ui.theme.DivaTheme

@Composable
fun DivaApp(
    context: CompositionLocalContext,
    theme: @Composable (content: @Composable () -> Unit) -> Unit = { content ->
        DivaTheme {
            content()
        }
    },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(context = context) {
        DivaApp(
            theme = theme,
            content = content
        )
    }
}

@Composable
fun DivaApp(
    vararg providedValues: ProvidedValue<*>,
    theme: @Composable (content: @Composable () -> Unit) -> Unit = { content ->
        DivaTheme {
            content()
        }
    },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(values = providedValues) {
        DivaApp(
            theme = theme,
            content = content
        )
    }
}

@Composable
fun DivaApp(
    theme: @Composable (content: @Composable () -> Unit) -> Unit = { content ->
        DivaTheme {
            content()
        }
    },
    content: @Composable () -> Unit
) {
    theme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            content()
        }
    }
}
