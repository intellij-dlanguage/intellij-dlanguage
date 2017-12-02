package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageConditionalDeclaration extends PsiElement {

    @Nullable
    public DLanguageCompileCondition getCompileCondition();

    @NotNull
    public List<DLanguageDeclaration> getDeclarations();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public PsiElement getKW_ELSE();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

}
