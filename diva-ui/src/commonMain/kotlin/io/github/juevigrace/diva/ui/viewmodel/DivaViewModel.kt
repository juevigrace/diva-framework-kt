package io.github.juevigrace.diva.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

// TODO: add error handling for the ui
abstract class DivaViewModel : ViewModel() {
    protected val scope = viewModelScope
}
