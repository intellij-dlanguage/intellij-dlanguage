package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageFundamentalTypeConstructExpression
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageFundamentalTypeConstructExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageFundamentalTypeConstructExpression {

    override fun getDType(): DType? = basicType?.dType
}
