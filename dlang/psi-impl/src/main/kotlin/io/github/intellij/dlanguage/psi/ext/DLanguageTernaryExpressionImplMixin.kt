package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageTernaryExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageTernaryExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageTernaryExpression {

    override fun getDType(): DType? {
        if (expressions.size != 3)
            return null
        // TODO handle aliased type
        if (expressions.get(1).dType == DPrimitiveType.fromText("void") || expressions.get(1).dType == DPrimitiveType.fromText("void"))
            return DPrimitiveType.fromText("void")
        // TODO actually it is the common type of both
        return expressions.get(1).dType
    }
}
