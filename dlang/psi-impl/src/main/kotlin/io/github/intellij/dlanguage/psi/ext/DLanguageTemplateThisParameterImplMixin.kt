package io.github.intellij.dlanguage.psi.ext

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import io.github.intellij.dlanguage.psi.DLanguageTemplateThisParameter
import io.github.intellij.dlanguage.psi.types.DType
import io.github.intellij.dlanguage.psi.types.DUnknownType

abstract class DLanguageTemplateThisParameterImplMixin(node: ASTNode) : ASTWrapperPsiElement(node),
    DLanguageTemplateThisParameter {

    override fun getDType(): DType {
        return DUnknownType() // TODO
    }
}
