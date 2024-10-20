package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageArrayAccessExpression
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DAssociativeArrayType
import io.github.intellij.dlanguage.psi.types.DPointerType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType

abstract class DLanguageArrayAccessExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageArrayAccessExpression {
    override fun getDType(): DType? {
        if (expression == null)
            return null
        var type = expression!!.dType
        // simple array access
        if (indexExpression == null || indexExpression!!.expressions.size == 1) {
            if (type is DArrayType)
                return type.base
            if (type is DAssociativeArrayType)
                return type.valueType
            return DUnknownType()
        }
        // slice
        if (type is DPointerType) {
            type = DArrayType(type.base, null)
        }
        if (type !is DArrayType)
            type = DArrayType(DUnknownType(), null)
        return type
    }
}
