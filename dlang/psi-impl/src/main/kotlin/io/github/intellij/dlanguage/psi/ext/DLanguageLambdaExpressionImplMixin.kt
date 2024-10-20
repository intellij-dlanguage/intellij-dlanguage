package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageLambdaExpression
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType

abstract class DLanguageLambdaExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageLambdaExpression {

    override fun getDType(): DType? {
        // TODO implement
        return DUnknownType()
    }
}
