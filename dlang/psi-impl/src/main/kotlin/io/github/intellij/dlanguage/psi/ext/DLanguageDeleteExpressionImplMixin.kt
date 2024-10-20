package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageDeleteExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageDeleteExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageDeleteExpression {
    override fun getDType(): DType? = DPrimitiveType.fromText("void")
}
