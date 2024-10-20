package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageParenthesisedExpression
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageParenthesisedExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageParenthesisedExpression {

    override fun getDType(): DType? = expression?.dType
}
