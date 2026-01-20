package io.github.intellij.dlanguage.features.documentation.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.util.PsiTreeUtil

class DDocParamsSectionImpl(node: ASTNode): DDocNamedSectionImpl(node) {

    fun getParameters(): List<DDocKeyValueImpl> {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DDocKeyValueImpl::class.java)
    }
}
