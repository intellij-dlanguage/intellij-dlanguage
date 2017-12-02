package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageForStatement extends PsiElement {

    @NotNull
    public List<DLanguageDeclarationOrStatement> getDeclarationOrStatements();

    @NotNull
    public List<DLanguageExpression> getExpressions();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

    @Nullable
    public PsiElement getKW_FOR();

    @Nullable
    public PsiElement getOP_SCOLON();

}
