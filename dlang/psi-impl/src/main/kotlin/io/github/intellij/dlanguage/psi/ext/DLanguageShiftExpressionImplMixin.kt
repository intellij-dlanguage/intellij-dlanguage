package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageShiftExpression
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageShiftExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageShiftExpression {

    override fun getDType(): DType? {
        // TODO integer promotion rule (dchar become uint)
        return DPrimitiveType.fromText("int")
    }
}
