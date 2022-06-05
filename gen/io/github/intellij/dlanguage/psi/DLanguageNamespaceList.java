package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface DLanguageNamespaceList extends PsiElement {

    @Nullable
    PsiElement getOP_COMMA();

    @Nullable
    List<DLanguageTernaryExpression> getTernaryExpressions();
}
