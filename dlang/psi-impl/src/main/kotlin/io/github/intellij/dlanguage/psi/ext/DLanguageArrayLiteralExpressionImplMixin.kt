package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageArrayLiteralExpression
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DAssociativeArrayType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType

abstract class DLanguageArrayLiteralExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageArrayLiteralExpression {

    override fun getDType(): DType? {
        // TODO determine the inner types
        if (assocArrayLiteral != null)
            return DAssociativeArrayType(DUnknownType(), DUnknownType())
        return DArrayType(DUnknownType(), null)
    }
}
