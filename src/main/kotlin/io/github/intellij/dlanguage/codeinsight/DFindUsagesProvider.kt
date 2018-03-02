package io.github.intellij.dlanguage.codeinsight

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.ElementDescriptionUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.tree.TokenSet
import com.intellij.usageView.UsageViewLongNameLocation
import com.intellij.usageView.UsageViewNodeTextLocation
import io.github.intellij.dlanguage.DLanguageLexerAdapter
import io.github.intellij.dlanguage.psi.DLanguageClassDeclaration
import io.github.intellij.dlanguage.psi.DLanguageIfCondition
import io.github.intellij.dlanguage.psi.DLanguageInterfaceDeclaration
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameter
import io.github.intellij.dlanguage.psi.DLanguageVariableDeclaration
import io.github.intellij.dlanguage.psi.DTokenSets
import io.github.intellij.dlanguage.psi.DlangCatch
import io.github.intellij.dlanguage.psi.DlangEnumDeclaration
import io.github.intellij.dlanguage.psi.DlangFunctionDeclaration
import io.github.intellij.dlanguage.psi.DlangIdentifier
import io.github.intellij.dlanguage.psi.DlangInterfaceOrClass
import io.github.intellij.dlanguage.psi.DlangParameter
import io.github.intellij.dlanguage.psi.DlangTemplateDeclaration
import io.github.intellij.dlanguage.psi.DlangTypes
import io.github.intellij.dlanguage.psi.DlangUnionDeclaration

class DFindUsagesProvider : FindUsagesProvider
{
    // Second parameter is nodes that are PsiNamedElements in practice.


    override fun getWordsScanner(): WordsScanner?
    {
        return DefaultWordsScanner(DLanguageLexerAdapter(),
                TokenSet.create(DlangTypes.IDENTIFIER),
                DTokenSets.LINE_COMMENTS, DTokenSets.BLOCK_COMMENTS, DTokenSets.STRING_LITERALS)
    }

    override fun canFindUsagesFor(psiElement: PsiElement): Boolean
    {
        return psiElement is PsiNamedElement
    }

    override fun getHelpId(psiElement: PsiElement): String?
    {
        // TODO: Use HelpID after 13.1.
        return "reference.dialogs.findUsages.other"
    }

    override fun getType(element: PsiElement): String
    {
        //        return ElementDescriptionUtil.getElementDescription(element, UsageViewTypeLocation.INSTANCE);
        //        return "woops";

        return if (element is DlangFunctionDeclaration)
        {
            "Function"
        } else if (element is DlangIdentifier)
        {
            "Identifier"
        } else if (element is DLanguageClassDeclaration)
        {
            "Class"
        } else if (element is DLanguageInterfaceDeclaration)
        {
            "Interface"
        } else if (element is DlangInterfaceOrClass)
        {
            if (element.getParent() is DLanguageInterfaceDeclaration)
            {
                "Interface"
            } else "Class"
        } else if (element is DlangEnumDeclaration)
        {
            "Enum"
        } else if (element is DLanguageTemplateParameter)
        {
            "Template Parameter"
        } else if (element is DlangTemplateDeclaration)
        {
            "Template"
        } else if (element is DlangParameter)
        {
            "Parameter"
        } else if (element is DlangUnionDeclaration)
        {
            "Union"
        } else if (element is DLanguageIfCondition)
        {
            "Variable"
        } else if (element is DlangCatch)
        {
            "Catch"
        } else if (element is DLanguageVariableDeclaration)
        {
            "Variable"
        } else
        {
            ""
        }

    }

    override fun getDescriptiveName(element: PsiElement): String
    {
        return ElementDescriptionUtil.getElementDescription(element, UsageViewLongNameLocation.INSTANCE)
        //        return "Totally rocks!";
    }

    override fun getNodeText(element: PsiElement, useFullName: Boolean): String
    {
        return ElementDescriptionUtil.getElementDescription(element, UsageViewNodeTextLocation.INSTANCE)
    }

    companion object
    {
        private val LOG = Logger.getInstance(DFindUsagesProvider::class.java)
    }
}

