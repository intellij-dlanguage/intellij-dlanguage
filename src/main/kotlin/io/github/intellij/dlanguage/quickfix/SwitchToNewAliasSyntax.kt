package io.github.intellij.dlanguage.quickfix

import com.intellij.codeInspection.LocalQuickFixOnPsiElement
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import io.github.intellij.dlanguage.psi.DLanguageAliasDeclaration
import io.github.intellij.dlanguage.psi.impl.DElementFactory

/**
 * Created by francis on 11/25/2017.
 */
class SwitchToNewAliasSyntax(elem: DLanguageAliasDeclaration) : LocalQuickFixOnPsiElement(elem) {
    //todo need to handle declarations like alias int a,b; better
    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val aliasDecl = getStartElement() as DLanguageAliasDeclaration
        val type = aliasDecl.type
        val aliasStartIndex = aliasDecl.textOffset
        val restStartIndex = aliasDecl.type!!.textRange.endOffset
        val restEndIndex = aliasDecl.textRange.endOffset
        val restText = aliasDecl.text.substring(restStartIndex - aliasStartIndex, restEndIndex - aliasStartIndex)
        val newAliasString = "alias " + restText.replace(";", "") + " = " + type!!.text + ";"
        aliasDecl.replace(DElementFactory.createAliasDeclarationFromText(project, newAliasString))
    }

    override fun getFamilyName(): String = "DLang"

    override fun getText(): String = "Switch to new alias syntax"
}
