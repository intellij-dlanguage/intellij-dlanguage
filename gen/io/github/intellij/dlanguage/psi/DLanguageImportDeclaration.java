package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangSingleImport;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
