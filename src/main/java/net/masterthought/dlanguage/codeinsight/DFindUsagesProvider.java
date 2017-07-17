package net.masterthought.dlanguage.codeinsight;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.ElementDescriptionUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.usageView.UsageViewLongNameLocation;
import com.intellij.usageView.UsageViewNodeTextLocation;
import net.masterthought.dlanguage.DLanguageLexerAdapter;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DFindUsagesProvider implements FindUsagesProvider {
    @SuppressWarnings("UnusedDeclaration")
    private final static Logger LOG = Logger.getInstance(DFindUsagesProvider.class);
    // Second parameter is nodes that are PsiNamedElements in practice.


    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(new DLanguageLexerAdapter(),
            TokenSet.create(DLanguageTypes.IDENTIFIER),
            DTokenSets.LINE_COMMENTS, DTokenSets.BLOCK_COMMENTS, DTokenSets.STRING_LITERALS);
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull PsiElement psiElement) {
        // TODO: Use HelpID after 13.1.
        return "reference.dialogs.findUsages.other";
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement element) {
//        return ElementDescriptionUtil.getElementDescription(element, UsageViewTypeLocation.INSTANCE);
//        return "woops";

        if (element instanceof DLanguageFunctionDeclaration) {
            return "Function";
        } else if (element instanceof DLanguageIdentifier) {
            return "Identifier";
        } else if (element instanceof DLanguageClassDeclaration) {
            return "Class";
        } else if (element instanceof DLanguageInterfaceDeclaration) {
            return "Interface";
        } else if (element instanceof DLanguageInterfaceOrClass) {
            if (element.getParent() instanceof DLanguageInterfaceDeclaration) {
                return "Interface";
            }
            return "Class";
        } else if (element instanceof DLanguageEnumDeclaration) {
            return "Enum";
        } else if (element instanceof DLanguageTemplateParameter) {
            return "Template Parameter";
        } else if (element instanceof DLanguageTemplateDeclaration) {
            return "Template";
        } else if (element instanceof DLanguageParameter) {
            return "Parameter";
        } else if (element instanceof DLanguageUnionDeclaration) {
            return "Union";
        } else if (element instanceof DLanguageIfCondition) {
            return "Variable";
        } else if (element instanceof DLanguageCatch) {
            return "Catch";
        } else if (element instanceof DLanguageVariableDeclaration) {
            return "Variable";
        } else {
            return "";
        }

    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {
        return ElementDescriptionUtil.getElementDescription(element, UsageViewLongNameLocation.INSTANCE);
//        return "Totally rocks!";
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        return ElementDescriptionUtil.getElementDescription(element, UsageViewNodeTextLocation.INSTANCE);
    }
}

