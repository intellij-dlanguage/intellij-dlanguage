package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFinalSwitchStatement extends PsiElement {

    @Nullable
    public PsiElement getKW_FINAL();

    @Nullable
    public DLanguageSwitchStatement getSwitchStatement();
}
