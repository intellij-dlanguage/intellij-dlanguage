package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageAndAndExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageAndAndExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageAndAndExpression {

    override fun getDType(): DType? {
        if (expressions.size != 2)
            return DPrimitiveType.fromText("bool")
        if (expressions[1].dType == DPrimitiveType.fromText("void"))
            return DPrimitiveType.fromText("void")
        return DPrimitiveType.fromText("bool")
    }
}
