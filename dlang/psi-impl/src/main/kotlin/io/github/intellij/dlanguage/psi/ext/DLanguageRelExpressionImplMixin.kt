package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageRelExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageRelExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageRelExpression {

    override fun getDType(): DType? = DPrimitiveType.fromText("bool")
}
