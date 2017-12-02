package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageConditionalDeclaration extends PsiElement {

    @Nullable
    DLanguageCompileCondition getCompileCondition();

    @NotNull
    List<DLanguageDeclaration> getDeclarations();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    PsiElement getKW_ELSE();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

}
