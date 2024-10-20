package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageDollarExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageDollarExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageDollarExpression {

    override fun getDType(): DType? = DPrimitiveType.fromText("int") // TODO size_t
}
