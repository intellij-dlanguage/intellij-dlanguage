package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageInitializer extends PsiElement {
    @Nullable
    PsiElement getKW_VOID();

    @Nullable
    DLanguageNonVoidInitializer getNonVoidInitializer();
}
