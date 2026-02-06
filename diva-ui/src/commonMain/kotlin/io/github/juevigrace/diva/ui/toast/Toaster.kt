package io.github.juevigrace.diva.ui.toast

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.getOrElse
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.ui.observable.ObserveFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString

data class ToastMessage(
    val message: StringResource,
    val actionLabel: Option<StringResource> = Option.None,
    val withDismissAction: Boolean = true,
    val duration: SnackbarDuration = if (actionLabel is Option.None) {
        SnackbarDuration.Short
    } else {
        SnackbarDuration.Indefinite
    },
    val isError: Boolean = false,
    val details: Option<StringResource> = Option.None,
)

interface Toaster {
    val messages: Flow<ToastMessage>

    suspend fun show(message: ToastMessage)

    companion object {
        operator fun invoke(): Toaster {
            return ToasterImpl
        }
    }
}

internal object ToasterImpl : Toaster {
    private val _messages: Channel<ToastMessage> = Channel()
    override val messages: Flow<ToastMessage> = _messages.receiveAsFlow()

    override suspend fun show(message: ToastMessage) {
        _messages.trySend(message)
    }
}

val LocalToaster: ProvidableCompositionLocal<Toaster> = compositionLocalOf { error("Toaster not provided") }

@Composable
fun Toaster(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val toaster: Toaster = LocalToaster.current

    Toaster(
        modifier = modifier,
        toaster = toaster,
        hostState = hostState,
    )
}

@Composable
fun Toaster(
    modifier: Modifier = Modifier,
    toaster: Toaster,
    hostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    val scope: CoroutineScope = rememberCoroutineScope()
    var isError: Boolean by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        onDispose {
            isError = false
        }
    }

    ObserveFlow(
        flow = toaster.messages,
        onEvent = { msg ->
            isError = msg.isError
            scope.launch {
                hostState.showSnackbar(
                    message = getString(msg.message),
                    actionLabel = msg.actionLabel.map { getString(it) }.getOrElse { null },
                    withDismissAction = msg.withDismissAction,
                    duration = msg.duration
                )
            }
        }
    )

    SnackbarHost(
        modifier = modifier,
        hostState = hostState,
        snackbar = { data ->
            Snackbar(
                snackbarData = data,
                containerColor = if (isError) {
                    MaterialTheme.colorScheme.errorContainer
                } else {
                    SnackbarDefaults.color
                },
                contentColor = if (isError) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    SnackbarDefaults.contentColor
                },
                actionColor = if (isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    SnackbarDefaults.actionColor
                },
                actionContentColor = if (isError) {
                    MaterialTheme.colorScheme.onError
                } else {
                    SnackbarDefaults.actionContentColor
                },
            )
        }
    )
}
