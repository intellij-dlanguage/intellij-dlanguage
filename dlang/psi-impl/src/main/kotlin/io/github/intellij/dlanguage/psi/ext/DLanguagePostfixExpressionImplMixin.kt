package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguagePostfixExpression
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguagePostfixExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguagePostfixExpression {

    override fun getDType(): DType? = expression?.dType
}
