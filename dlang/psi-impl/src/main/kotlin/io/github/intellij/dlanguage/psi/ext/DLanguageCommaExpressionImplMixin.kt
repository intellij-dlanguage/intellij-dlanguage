package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageCommaExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType


abstract class DLanguageCommaExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageCommaExpression {

    override fun getDType(): DType? = DPrimitiveType.fromText("void")
}
