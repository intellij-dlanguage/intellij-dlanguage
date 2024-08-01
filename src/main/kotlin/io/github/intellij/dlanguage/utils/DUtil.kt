package io.github.intellij.dlanguage.utils

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.util.PsiTreeUtil
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain
import io.github.intellij.dlanguage.psi.DLanguageTemplateMixinExpression
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement
import io.github.intellij.dlanguage.psi.interfaces.Declaration
import io.github.intellij.dlanguage.psi.named.*
import java.util.*

/**
 * General util class. Provides methods for finding named nodes in the Psi tree.
 */
object DUtil {
    /**
     * Tells whether a named node is a definition node based on its context.
     *
     *
     * Precondition: Element is in a DLanguage file.
     */
    fun definitionNode(e: PsiNamedElement): Boolean {
        return e is Declaration
    }

    /**
     * Tells whether a node is a definition node based on its context.
     */
    fun definitionNode(node: ASTNode): Boolean {
        val element = node.psi
        return element is PsiNamedElement && definitionNode(
            element
        )
    }

    @JvmStatic
    fun isNotNullOrEmpty(str: String?): Boolean {
        return (str != null && !str.isEmpty())
    }


    @JvmStatic
    fun isDunitTestFile(psiFile: PsiFile?): Boolean {
        val cds = PsiTreeUtil.findChildrenOfType(psiFile, DlangClassDeclaration::class.java)
        for (cd in cds) {
            // if a class contains the UnitTest mixin assume its a valid d-unit test class
            val tmis = PsiTreeUtil.findChildrenOfType(cd, DLanguageTemplateMixinExpression::class.java)
            for (tmi in tmis) {
                if (tmi.text.contains("UnitTest")) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * @param namedElement constructor, or method contained within a class or struct
     * @return the class or struct containing this constructor/method. returns null if not found
     */
    @JvmStatic
    fun getParentClassOrStructOrTemplateOrInterfaceOrUnion(namedElement: PsiElement?): DNamedElement? {
        return PsiTreeUtil.getParentOfType(
            namedElement,
            DlangClassDeclaration::class.java,
            DlangStructDeclaration::class.java,
            DlangTemplateDeclaration::class.java,
            DlangUnionDeclaration::class.java
        )
    }

    fun getPrevSiblingOfType(
        child: ASTNode?,
        type: IElementType?
    ): ASTNode? {
        if (child == null) return null
        if (child.elementType === type) {
            return child.treePrev
        }
        return getPrevSiblingOfType(child.treePrev, type)
    }

    fun getPrevSiblingOfType(
        child: ASTNode?,
        type: IElementType,
        excluded: HashSet<IElementType?>?
    ): ASTNode? {
        if (child == null) return null
        if (child.elementType === type) {
            return child
        }
        if (excluded != null && excluded.contains(child.elementType)) {
            return null
        }
        return getPrevSiblingOfType(child.treePrev, type, excluded)
    }

    fun getPrevSiblingOfTypes(
        child: ASTNode?,
        newHashSet: HashSet<IElementType?>,
        excluded: HashSet<IElementType?>?
    ): ASTNode? {
        if (child == null) return null
        if (newHashSet.contains(child.elementType)) {
            return child
        }
        if (excluded != null && excluded.contains(child.elementType)) {
            return null
        }
        return getPrevSiblingOfTypes(child.treePrev, newHashSet, excluded)
    }

    @JvmStatic
    fun <T : PsiElement?> findParentOfType(element: PsiElement, className: Class<T>): T? {
        if (className.isInstance(element)) {
            return element as T
        }

        return Optional.ofNullable(element.parent)
            .map { parent: PsiElement -> findParentOfType(parent, className) }
            .orElse(null)
    }

    /**
     * D file names should be composed of the ASCII characters lower case letters, digits or _ and should also not be a Keyword.
     * @param name a filename to check
     * @return true if filename
     */
    @JvmStatic
    fun isValidDlangFileName(name: String): Boolean {
        return name.matches("[a-zA-Z_0-9]+(\\.di|\\.d)?".toRegex())
    }
}

