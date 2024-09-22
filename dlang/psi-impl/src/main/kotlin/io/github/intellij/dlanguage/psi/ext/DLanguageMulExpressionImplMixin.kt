package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageMulExpression
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageMulExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageMulExpression {

    override fun getDType(): DType? {
        // TODO (arithmetic conversion)
        return expressions.first().dType
    }
}
