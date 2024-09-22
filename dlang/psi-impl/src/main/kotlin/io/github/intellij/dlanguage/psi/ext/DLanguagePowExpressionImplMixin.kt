package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguagePowExpression
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguagePowExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguagePowExpression {

    override fun getDType(): DType? = expressions.first().dType
}
