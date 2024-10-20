package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageThrowExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageThrowExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageThrowExpression {
    override fun getDType(): DType? = DPrimitiveType.fromText("void") // Note: actually noreturn but void is fine for now
}
