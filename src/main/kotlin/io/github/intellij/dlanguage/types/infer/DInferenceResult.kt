package io.github.intellij.dlanguage.types.infer

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.psi.DLanguageDeclaration
import io.github.intellij.dlanguage.types.DType
import io.github.intellij.dlanguage.types.DTypeUnknown

class DInferenceResult(
    private val declarationTypes: Map<DLanguageDeclaration, DType>
) {
    fun getDeclarationType(expr: PsiElement): DType =
        declarationTypes[expr] ?: DTypeUnknown
}
