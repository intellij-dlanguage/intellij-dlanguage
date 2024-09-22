package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageIsExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageIsExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageIsExpression {

    override fun getDType(): DType? = DPrimitiveType.fromText("bool")
}
