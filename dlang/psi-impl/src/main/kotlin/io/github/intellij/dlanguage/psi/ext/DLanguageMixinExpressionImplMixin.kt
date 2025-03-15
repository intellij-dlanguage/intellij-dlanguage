package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageMixinExpression
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageMixinExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageMixinExpression {

    override fun getDType(): DType? = null
}
