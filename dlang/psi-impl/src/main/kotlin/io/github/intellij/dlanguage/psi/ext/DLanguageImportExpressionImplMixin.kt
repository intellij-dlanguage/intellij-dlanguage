package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageImportExpression
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DPrimitiveType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageImportExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageImportExpression {
    override fun getDType(): DType? = DArrayType(DPrimitiveType.fromText("char"), null) // TODO hex string
}
