package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageNewExpression
import io.github.intellij.dlanguage.psi.types.DPointerType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.UserDefinedDType
import io.github.intellij.dlanguage.utils.ClassDeclaration

abstract class DLanguageNewExpressionImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageNewExpression {

    override fun getDType(): DType? {
        val dtype = type?.dType?: return null
        // TODO handle aliased type
        val resolved = (dtype as? UserDefinedDType)?.resolve()
        if (resolved is ClassDeclaration) {
            // it is a reference type
            return dtype
        }
        return DPointerType(dtype)
    }
}
