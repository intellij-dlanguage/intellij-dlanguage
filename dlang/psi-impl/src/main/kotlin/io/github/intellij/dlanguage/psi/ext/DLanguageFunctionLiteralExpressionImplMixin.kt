package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageFunctionLiteralExpression
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType

abstract class DLanguageFunctionLiteralExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageFunctionLiteralExpression {
    override fun getDType(): DType? = DUnknownType() // TODO
}
