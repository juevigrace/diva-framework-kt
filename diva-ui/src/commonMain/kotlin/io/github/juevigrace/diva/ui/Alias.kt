package io.github.juevigrace.diva.ui

import androidx.compose.runtime.Composable
import io.github.juevigrace.diva.core.Option

typealias ComposableFunc = @Composable () -> Unit
typealias ComposableFuncWithParam<T> = @Composable (T) -> Unit
typealias OptionalComposableFunc = Option<ComposableFunc>
typealias OptionalComposableFuncWithParam<T> = Option<ComposableFuncWithParam<T>>

