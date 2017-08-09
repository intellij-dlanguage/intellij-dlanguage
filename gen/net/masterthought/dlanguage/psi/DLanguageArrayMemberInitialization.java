package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageArrayMemberInitialization extends PsiElement {
    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageNonVoidInitializer getNonVoidInitializer();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

}
