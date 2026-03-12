package io.github.juevigrace.diva.core.validation

interface Validator<F, T : ValidationResult> {
    fun validate(form: F): T
}

interface ValidationResult {
    val hasErrors: Boolean
    fun valid(): Boolean
}
