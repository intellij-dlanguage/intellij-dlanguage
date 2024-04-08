package io.github.intellij.dlanguage.codeinsight;

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
import io.github.intellij.dlanguage.DLanguageLexerAdapter;
import io.github.intellij.dlanguage.psi.DLanguageClassDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageIfCondition;
import io.github.intellij.dlanguage.psi.DLanguageInterfaceDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageTemplateParameter;
import io.github.intellij.dlanguage.psi.DLanguageVariableDeclaration;
import io.github.intellij.dlanguage.psi.DTokenSets;
import io.github.intellij.dlanguage.psi.named.DlangCatch;
import io.github.intellij.dlanguage.psi.named.DlangEnumDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangFunctionDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceOrClass;
import io.github.intellij.dlanguage.psi.named.DlangParameter;
import io.github.intellij.dlanguage.psi.named.DlangTemplateDeclaration;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.named.DlangUnionDeclaration;
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
            TokenSet.create(DlangTypes.IDENTIFIER),
            DTokenSets.LINE_COMMENTS, DTokenSets.BLOCK_COMMENTS, DTokenSets.STRING_LITERALS);
    }

    @Override
    public boolean canFindUsagesFor(@NotNull final PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull final PsiElement psiElement) {
        // TODO: Use HelpID after 13.1.
        return "reference.dialogs.findUsages.other";
    }

    @NotNull
    @Override
    public String getType(@NotNull final PsiElement element) {
//        return ElementDescriptionUtil.getElementDescription(element, UsageViewTypeLocation.INSTANCE);
//        return "woops";

        if (element instanceof DlangFunctionDeclaration) {
            return "Function";
        } else if (element instanceof DlangIdentifier) {
            return "Identifier";
        } else if (element instanceof DLanguageClassDeclaration) {
            return "Class";
        } else if (element instanceof DLanguageInterfaceDeclaration) {
            return "Interface";
        } else if (element instanceof DlangInterfaceOrClass) {
            if (element.getParent() instanceof DLanguageInterfaceDeclaration) {
                return "Interface";
            }
            return "Class";
        } else if (element instanceof DlangEnumDeclaration) {
            return "Enum";
        } else if (element instanceof DLanguageTemplateParameter) {
            return "Template Parameter";
        } else if (element instanceof DlangTemplateDeclaration) {
            return "Template";
        } else if (element instanceof DlangParameter) {
            return "Parameter";
        } else if (element instanceof DlangUnionDeclaration) {
            return "Union";
        } else if (element instanceof DLanguageIfCondition) {
            return "Variable";
        } else if (element instanceof DlangCatch) {
            return "Catch";
        } else if (element instanceof DLanguageVariableDeclaration) {
            return "Variable";
        } else {
            return "";
        }

    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull final PsiElement element) {
        return ElementDescriptionUtil.getElementDescription(element, UsageViewLongNameLocation.INSTANCE);
//        return "Totally rocks!";
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull final PsiElement element, final boolean useFullName) {
        return ElementDescriptionUtil.getElementDescription(element, UsageViewNodeTextLocation.INSTANCE);
    }
}

