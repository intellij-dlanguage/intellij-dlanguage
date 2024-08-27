package io.github.intellij.dlanguage.features.documentation.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiUtilCore
import com.intellij.psi.util.elementType
import io.github.intellij.dlanguage.documentation.psi.DDocMetaElementTypes
import io.github.intellij.dlanguage.documentation.psi.DlangDocElementType
import io.github.intellij.dlanguage.features.documentation.psi.DlangDocPsiElement

open class DlangDocPsiElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), DlangDocPsiElement {
    override fun getDescriptionElements(): Array<PsiElement> {
        val array = ArrayList<PsiElement>()
        var child = firstChild
        while (child != null && child.elementType is DlangDocElementType) {
            if (child.elementType != DDocMetaElementTypes.DDOC_COMMENT_START && child.elementType != DDocMetaElementTypes.DDOC_COMMENT_END && child.elementType != DDocMetaElementTypes.DDOC_COMMENT_LEADING_ASTERISKS) {
                array.add(child)
            }
            child = child.nextSibling
        }
        return PsiUtilCore.toPsiElementArray(array)
    }
}
