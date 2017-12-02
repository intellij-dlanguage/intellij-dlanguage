package io.github.intellij.dlanguage.types.infer.context

import io.github.intellij.dlanguage.psi.DLanguageDeclaration
import io.github.intellij.dlanguage.psi.DLanguageFunctionDeclaration
import io.github.intellij.dlanguage.types.DType
import io.github.intellij.dlanguage.types.infer.DInferenceResult

class DInferenceContext {
    private val declarationTypes: MutableMap<DLanguageDeclaration, DType> = HashMap()

    fun infer(fn: DLanguageFunctionDeclaration): DInferenceResult {
        val block = fn.functionBody?.blockStatement ?: return createInferenceResult()
        return createInferenceResult()
    }

    private fun createInferenceResult(): DInferenceResult {
        return DInferenceResult(declarationTypes)
    }

    fun writeType(psi: DLanguageDeclaration, type: DType) {
        declarationTypes[psi] = type
    }
}
