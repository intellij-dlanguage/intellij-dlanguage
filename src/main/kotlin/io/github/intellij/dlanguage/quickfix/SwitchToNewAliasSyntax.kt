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

    /*
    * There are various ways in which an alias can be defined. This class need vigorous testing!
    *
    * Check for the following:
    *
    * alias myint = abc.Foo.bar;
    * alias fp = i => 1;
    * alias fp = (i) { return 1; };
    *
    * Also, for situations in which the standard syntax cannot be used (such as an alias for a literal)
    * there is std.meta.Alias that can be used:
    *
    * alias b = Alias!4;
    *
    * not to mention std.meta.AliasSeq:
    *
    * alias TL = AliasSeq!(int, double); // in previous versions of Phobos, this was known as TypeTuple.
    *
    * todo: add testing and fix this code to support various scenarios like "alias int a,b;"
    */
    override fun invoke(project: Project, file: PsiFile, startElement: PsiElement, endElement: PsiElement) {
        val aliasDecl = getStartElement() as DLanguageAliasDeclaration
        val type = aliasDecl.type
        val aliasStartIndex = aliasDecl.textOffset
        val restStartIndex = aliasDecl.type!!.textRange.endOffset
        val restEndIndex = aliasDecl.textRange.endOffset
        val restText = aliasDecl.text.substring(restStartIndex - aliasStartIndex, restEndIndex - aliasStartIndex)
        val newAliasString = "alias " + restText.replace(";", "").trim() + " = " + type!!.text + ";"

        DElementFactory.createAliasDeclarationFromText(project, newAliasString)?.let {
            aliasDecl.replace(it)
        }
    }

    override fun getFamilyName(): String = "DLang"

    override fun getText(): String = "Switch to new alias syntax"
}
