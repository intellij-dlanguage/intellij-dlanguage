package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageNewExpression extends PsiElement {
    @Nullable
    public PsiElement getKW_NEW();

    @Nullable
    public DLanguageNewAnonClassExpression getNewAnonClassExpression();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public DLanguageArguments getArguments();

    @Nullable
    public PsiElement getOP_BRACKET_LEFT();

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT();

}
