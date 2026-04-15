@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

package io.github.juevigrace.diva.core.util

@JsName("Console")
external interface JsConsole {
    fun log(vararg args: String)
    fun warn(vararg args: String)
    fun error(vararg args: String)
}

private val console: JsConsole = js("console")

object WasmJsDivaLogger : DivaLoggerBase() {
    override fun logToPlatform(tag: String, message: String, level: LogLevel) {
        val output = "[$tag] $message"
        when (level) {
            LogLevel.DEBUG -> console.log(output)
            LogLevel.INFO -> console.log(output)
            LogLevel.WARN -> console.warn(output)
            LogLevel.ERROR -> console.error(output)
            LogLevel.FATAL -> console.error(output)
        }
    }
}

actual fun createDivaLogger(): DivaLogger {
    return WasmJsDivaLogger
}