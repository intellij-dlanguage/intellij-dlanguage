package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageUnaryExpression
import io.github.intellij.dlanguage.psi.types.DPointerType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType

abstract class DLanguageUnaryExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageUnaryExpression {

    override fun getDType(): DType? {
        // TODO handle operator overloading
        if (oP_ASTERISK != null) {
            val type = expression?.dType ?:return null
            if (type is DPointerType) {
                return type.base
            }
            // to dereference a pointer, it must be a pointer
            return DUnknownType()
        }
        if (oP_AND != null) {
            return DPointerType(expression?.dType?: DUnknownType())
        }
        return return expression?.dType
    }
}
