package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTryStatement extends PsiElement {
    @Nullable
    public PsiElement getKW_TRY();

    @Nullable
    public DLanguageDeclarationOrStatement getDeclarationOrStatement();

    @Nullable
    public DLanguageCatches getCatches();

    @Nullable
    public DLanguageFinally getFinally();
}
