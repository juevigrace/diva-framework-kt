package io.github.juevigrace.diva.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import io.github.juevigrace.diva.ui.theme.DivaTheme
import io.github.juevigrace.diva.ui.toast.LocalToaster
import io.github.juevigrace.diva.ui.toast.Toaster

// todo: fixes

@Composable
fun <T : NavKey> DivaApp(
    vararg providedValues: ProvidedValue<*> = emptyArray(),
    theme: @Composable (content: @Composable () -> Unit) -> Unit = { content ->
        DivaTheme(content = content)
    },
    content: @Composable () -> Unit
) {
    ProvidableDivaApp(
        *providedValues,
        theme = theme,
        content = content
    )
}

@Composable
fun SimpleDivaApp(
    theme: @Composable (content: @Composable () -> Unit) -> Unit = { content ->
        DivaTheme(content = content)
    },
    content: @Composable () -> Unit
) {
    DivaAppInternal(
        theme = theme,
        content = content
    )
}

@Composable
fun ProvidableDivaApp(
    vararg providedValues: ProvidedValue<*> = emptyArray(),
    theme: @Composable (content: @Composable () -> Unit) -> Unit = { content ->
        DivaTheme(content = content)
    },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        *providedValues,
        LocalToaster provides Toaster.invoke()
    ) {
        DivaAppInternal(
            theme = theme,
            content = content
        )
    }
}

@Composable
internal fun DivaAppInternal(
    theme: @Composable (content: @Composable () -> Unit) -> Unit,
    content: @Composable () -> Unit
) {
    theme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { Toaster() },
            ) { innerPadding ->
                Box(
                    modifier = Modifier.padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}
