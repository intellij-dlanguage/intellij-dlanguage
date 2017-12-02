package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageImportDeclaration extends PsiElement {

    @Nullable
    public PsiElement getKW_IMPORT();

    @NotNull
    public List<DLanguageSingleImport> getSingleImports();

    @Nullable
    public DLanguageImportBindings getImportBindings();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

    @Nullable
    public PsiElement getOP_SCOLON();

}
