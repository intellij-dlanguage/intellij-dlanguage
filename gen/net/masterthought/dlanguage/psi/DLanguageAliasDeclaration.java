package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


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
    List<DLanguageAliasInitializer> getAliasInitializers();
}
