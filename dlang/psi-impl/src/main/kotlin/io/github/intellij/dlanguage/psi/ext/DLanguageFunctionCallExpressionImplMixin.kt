package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageFunctionCallExpression
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageFunctionCallExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageFunctionCallExpression {

    override fun getDType(): DType? {
        return expression?.dType
    }
}
