package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageImportDeclaration extends PsiElement {
    @Nullable
    PsiElement getKW_IMPORT();

    @NotNull
    List<DlangSingleImport> getSingleImports();

    @Nullable
    DLanguageImportBindings getImportBindings();

    @NotNull
    List<PsiElement> getOP_COMMAs();

    @Nullable
    PsiElement getOP_SCOLON();

}
