package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAnonymousEnumDeclaration extends PsiElement {
    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

}
