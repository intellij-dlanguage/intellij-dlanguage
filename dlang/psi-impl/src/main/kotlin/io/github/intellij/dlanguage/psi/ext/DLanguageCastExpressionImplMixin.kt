package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.utils.CastExpression

abstract class DLanguageCastExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    CastExpression {

    override fun getDType(): DType? {
        return type?.dType
    }

}
