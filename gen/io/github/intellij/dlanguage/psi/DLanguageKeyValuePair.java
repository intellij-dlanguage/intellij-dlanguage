package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageKeyValuePair extends PsiElement {

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    public PsiElement getOP_COLON();

}
