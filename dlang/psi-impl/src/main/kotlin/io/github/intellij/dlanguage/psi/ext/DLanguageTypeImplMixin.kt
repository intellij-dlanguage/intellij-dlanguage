package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageType
import io.github.intellij.dlanguage.psi.types.DArrayType
import io.github.intellij.dlanguage.psi.types.DPointerType
import io.github.intellij.dlanguage.psi.types.DType

abstract class DLanguageTypeImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageType {

    override fun getDType(): DType {
        // TODO type constructor
        var dtype = basicType!!.dType
        for (suffix in typeSuffixs) {
                if (suffix.getOP_ASTERISK() != null)
                    dtype = DPointerType(dtype)
                else if (suffix.getOP_BRACKET_LEFT() != null)
                    dtype = DArrayType(dtype, null) // TODO size
                // TODO others
            }
        return dtype
    }
}
