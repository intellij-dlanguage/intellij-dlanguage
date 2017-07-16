package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


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
