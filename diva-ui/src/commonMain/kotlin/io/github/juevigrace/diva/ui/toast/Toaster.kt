package io.github.juevigrace.diva.ui.toast

import androidx.compose.material3.SnackbarDuration
import io.github.juevigrace.diva.core.Option
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.StringResource

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
