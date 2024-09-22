package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression
import io.github.intellij.dlanguage.psi.types.DType


abstract class DLanguageAssignExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageAssignExpression {
    override fun getDType(): DType? = expressions.first().dType
}
