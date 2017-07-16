package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageConditionalDeclaration extends PsiElement {
    @Nullable
    public DLanguageCompileCondition getCompileCondition();

    @NotNull
    public List<DLanguageDeclaration> getDeclarations();

    @Nullable
    public PsiElement getOP_COLON();

    @Nullable
    public PsiElement getKW_ELSE();

}
