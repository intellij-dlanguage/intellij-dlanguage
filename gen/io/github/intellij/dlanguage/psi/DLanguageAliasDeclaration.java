package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangAliasInitializer;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAliasDeclaration extends PsiElement {

    @Nullable
    DLanguageIdentifierList getIdentifierList();

    @Nullable
    PsiElement getOP_COMMA();

    @NotNull
    List<DLanguageStorageClass> getStorageClasss();

    @Nullable
    PsiElement getKW_ALIAS();

    @Nullable
    DLanguageType getType();

    @Nullable
    PsiElement getOP_SCOLON();

    @NotNull
    List<DlangAliasInitializer> getAliasInitializers();
}
