package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by francis on 1/17/2018.
 */
public interface DLangStaticForeachStatement extends PsiElement {

    @NotNull
    DLanguageForeachStatement getForeachStatement();

    @NotNull
    PsiElement getKW_STATIC();

}
