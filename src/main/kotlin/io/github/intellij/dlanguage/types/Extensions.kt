package io.github.intellij.dlanguage.types

import io.github.intellij.dlanguage.psi.DLanguageAssignExpression
import io.github.intellij.dlanguage.psi.DLanguageInitializer
import io.github.intellij.dlanguage.psi.DLanguagePrimaryExpression
import io.github.intellij.dlanguage.psi.DLanguageUnaryExpression
import io.github.intellij.dlanguage.types.infer.DInferenceResult
import io.github.intellij.dlanguage.types.infer.inferExprType

// TODO: caching
val DLanguageUnaryExpression.dinference: DInferenceResult?
    get() = null

val DLanguagePrimaryExpression.dinference: DInferenceResult?
    get() = null

val DLanguageUnaryExpression.dtype: DType
    get() = inferExprType(this)

val DLanguagePrimaryExpression.dtype: DType
    get() = inferExprType(this)

val DLanguageAssignExpression.dtype: DType
    get() = inferExprType(this)

val DLanguageInitializer.dtype: DType
    get() = inferExprType(this)
