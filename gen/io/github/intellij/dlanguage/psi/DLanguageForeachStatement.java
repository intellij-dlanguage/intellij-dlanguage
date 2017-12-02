package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageForeachStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_FOREACH();

    @Nullable
    public PsiElement getKW_FOREACH_REVERSE();

    @Nullable
    public DLanguageDeclarationOrStatement getDeclarationOrStatement();

    @NotNull
    public List<DLanguageExpression> getExpressions();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

    @Nullable
    public PsiElement getOP_DDOT();

    @Nullable
    public DLanguageForeachType getForeachType();

    @Nullable
    public DLanguageForeachTypeList getForeachTypeList();

    @Nullable
    public PsiElement getOP_SCOLON();

}
