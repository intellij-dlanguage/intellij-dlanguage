package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructExpression
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageTypeConstructExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageTypeConstructExpression {

    override fun getDType(): DType? {
        return type?.dType // TODO type qualifier
    }
}
