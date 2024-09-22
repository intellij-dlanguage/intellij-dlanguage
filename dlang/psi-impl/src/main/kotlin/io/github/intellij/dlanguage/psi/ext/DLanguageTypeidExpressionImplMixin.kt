package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageTypeidExpression
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType

abstract class DLanguageTypeidExpressionImplMixin(node: ASTNode) :  ASTWrapperPsiElement(node),
    DLanguageTypeidExpression {
    override fun getDType(): DType? {
        return DUnknownType() // TODO Typeinfo instance (typeinfo class being defined in object)
    }
}
