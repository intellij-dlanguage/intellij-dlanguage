package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageAddExpression
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageAddExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageAddExpression {

    override fun getDType(): DType? {
        // TODO (arithmetic conversion)
        return expressions.first().dType
    }
}
