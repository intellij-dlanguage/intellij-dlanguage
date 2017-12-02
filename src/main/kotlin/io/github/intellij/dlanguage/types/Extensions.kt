package io.github.intellij.dlanguage.types

import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import io.github.intellij.dlanguage.psi.*
import io.github.intellij.dlanguage.psi.ext.parentOfType
import io.github.intellij.dlanguage.types.infer.DInferenceResult
import io.github.intellij.dlanguage.types.infer.inferDeclarationType
import io.github.intellij.dlanguage.types.infer.inferExprType
import io.github.intellij.dlanguage.types.infer.inferTypesIn

val DLanguageFunctionDeclaration.dinference: DInferenceResult?
    get() = CachedValuesManager.getCachedValue(this, {
        CachedValueProvider.Result.create(inferTypesIn(this), PsiModificationTracker.MODIFICATION_COUNT)
    })

val DLanguageUnaryExpression.dinference: DInferenceResult?
    get() = parentOfType<DLanguageFunctionDeclaration>()?.dinference

val DLanguagePrimaryExpression.dinference: DInferenceResult?
    get() = parentOfType<DLanguageFunctionDeclaration>()?.dinference

val DLanguageUnaryExpression.dtype: DType
    get() = inferExprType(this)

val DLanguagePrimaryExpression.dtype: DType
    get() = inferExprType(this)

val DLanguageAssignExpression.dtype: DType
    get() = inferExprType(this)

val DLanguageInitializer.dtype: DType
    get() = inferExprType(this)

val DLanguageDeclaration.dtype: DType
    get() = inferDeclarationType(this)
