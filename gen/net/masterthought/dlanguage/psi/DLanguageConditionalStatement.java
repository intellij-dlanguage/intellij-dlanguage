package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageConditionalStatement extends PsiElement {
    @Nullable
    public DLanguageCompileCondition getCompileCondition();

    @NotNull
    public List<DLanguageDeclarationOrStatement> getDeclarationOrStatements();

    @Nullable
    public PsiElement getKW_ELSE();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

}
