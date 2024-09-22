package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageMagicLiteralExpression
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageMagicLiteralExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageMagicLiteralExpression {

    override fun getDType(): DType? = DArrayType(DPrimitiveType.fromText("char"), null) // TODO string
}
