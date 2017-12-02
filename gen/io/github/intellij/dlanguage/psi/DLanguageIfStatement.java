package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageIfStatement extends PsiElement {

    @NotNull
    public List<DLanguageDeclarationOrStatement> getDeclarationOrStatements();

    @Nullable
    public PsiElement getKW_ELSE();

    @Nullable
    public PsiElement getKW_IF();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public DLanguageIfCondition getIfCondition();
}
