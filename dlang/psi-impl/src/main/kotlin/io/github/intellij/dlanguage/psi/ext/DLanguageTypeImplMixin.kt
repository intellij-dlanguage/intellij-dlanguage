package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageType
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.getDtypeWithSuffixes

abstract class DLanguageTypeImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageType {

    override fun getDType(): DType {
        // TODO type constructor
        var dtype = basicType!!.dType
        return getDtypeWithSuffixes(dtype, typeSuffixs)
    }
}
