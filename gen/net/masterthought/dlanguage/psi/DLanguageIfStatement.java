package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageIfStatement extends PsiElement {
    @NotNull
    List<DLanguageDeclarationOrStatement> getDeclarationOrStatements();

    @Nullable
    PsiElement getKW_ELSE();

    @Nullable
    PsiElement getKW_IF();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    DLanguageIfCondition getIfCondition();
}
