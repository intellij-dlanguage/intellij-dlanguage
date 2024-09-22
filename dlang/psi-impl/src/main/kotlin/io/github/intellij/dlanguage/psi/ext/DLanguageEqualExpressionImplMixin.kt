package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageEqualExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageEqualExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageEqualExpression {

    override fun getDType(): DType? = DPrimitiveType.fromText("bool")
}
