package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageArrayMemberInitialization extends PsiElement {

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public DLanguageNonVoidInitializer getNonVoidInitializer();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

}
