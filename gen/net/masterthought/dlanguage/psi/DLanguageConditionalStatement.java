package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageConditionalStatement extends PsiElement {
    @Nullable
    DLanguageCompileCondition getCompileCondition();

    @NotNull
    List<DLanguageDeclarationOrStatement> getDeclarationOrStatements();

    @Nullable
    PsiElement getKW_ELSE();

}
