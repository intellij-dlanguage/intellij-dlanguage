package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageImportBindings extends PsiElement {
    @NotNull
    List<PsiElement> getOP_COMMAs();

    @NotNull
    List<DLanguageImportBind> getImportBinds();

    @Nullable
    DLanguageSingleImport getSingleImport();

    @Nullable
    PsiElement getOP_COLON();

}
