package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDeprecated extends PsiElement {

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public DLanguageDeprecated getDeprecated();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
