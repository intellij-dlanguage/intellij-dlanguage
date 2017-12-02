package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageDeleteExpression extends PsiElement {

    @Nullable
    PsiElement getKW_DELETE();

    @Nullable
    DLanguageUnaryExpression getUnaryExpression();
}
