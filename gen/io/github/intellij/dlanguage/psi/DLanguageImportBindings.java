package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
