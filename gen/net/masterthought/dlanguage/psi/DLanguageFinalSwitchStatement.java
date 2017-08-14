package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageFinalSwitchStatement extends PsiElement {
    @Nullable
    PsiElement getKW_FINAL();

    @Nullable
    DLanguageSwitchStatement getSwitchStatement();
}
