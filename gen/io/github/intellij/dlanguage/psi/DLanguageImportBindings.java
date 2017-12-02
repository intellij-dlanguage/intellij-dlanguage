package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageImportBindings extends PsiElement {

    @NotNull
    public List<PsiElement> getOP_COMMAs();

    @NotNull
    public List<DLanguageImportBind> getImportBinds();

    @Nullable
    public DLanguageSingleImport getSingleImport();

    @Nullable
    public PsiElement getOP_COLON();

}
