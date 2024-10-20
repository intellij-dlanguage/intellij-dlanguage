package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageIdentityExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageIdentityExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageIdentityExpression {

    override fun getDType(): DType? = DPrimitiveType.fromText("bool")
}
