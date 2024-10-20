package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageTypeofExpression
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType

abstract class DLanguageTypeofExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageTypeofExpression {
    override fun getDType(): DType? {
        return DUnknownType() // TODO TypeDType
    }
}
