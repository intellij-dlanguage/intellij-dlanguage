package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAnonymousEnumDeclaration extends PsiElement {
    @Nullable
    DLanguageAssignExpression getAssignExpression();

    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

}
