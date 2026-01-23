package io.github.intellij.dlanguage.features.documentation.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiUtilCore
import com.intellij.psi.util.childLeafs
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.features.documentation.DDocElementTypes.DDOC_EQ

class DDocKeyValueImpl(node: ASTNode) : ASTWrapperPsiElement(node) {

    override fun getName(): String {
        return firstChild.text
    }

    fun getDescriptionElements(): Array<PsiElement> {
        // drop name and `=`, but a space may be between the two
        var list = this.childLeafs().drop(2).toList()
        if (list.first().elementType == DDOC_EQ)
            list = list.subList(1, list.size)
        return PsiUtilCore.toPsiElementArray(list)
    }
}
