package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageAssertExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageAssertExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageAssertExpression {
    override fun getDType(): DType? = DPrimitiveType.fromText("void");
}
