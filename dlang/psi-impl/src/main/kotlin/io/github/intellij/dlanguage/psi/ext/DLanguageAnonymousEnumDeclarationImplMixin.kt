package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageAnonymousEnumDeclaration
import io.github.intellij.dlanguage.psi.types.DPrimitiveType.Companion.INT
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageAnonymousEnumDeclarationImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageAnonymousEnumDeclaration {

    override fun getDType(): DType {
        if (getType() != null)
            return getType()!!.getDType()
        return INT
    }
}
