package io.github.intellij.dlanguage.types.infer

import com.intellij.psi.PsiElement
import io.github.intellij.dlanguage.types.DType
import io.github.intellij.dlanguage.types.DTypeUnknown

class DInferenceResult(
    private val exprTypes: Map<PsiElement, DType>
) {
    fun getExprType(expr: PsiElement): DType =
        exprTypes[expr] ?: DTypeUnknown
}
