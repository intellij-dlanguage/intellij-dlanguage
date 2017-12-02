package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageAliasDeclaration extends PsiElement {

    @Nullable
    public DLanguageIdentifierList getIdentifierList();

    @Nullable
    public PsiElement getOP_COMMA();

    @NotNull
    public List<DLanguageStorageClass> getStorageClasss();

    @Nullable
    public PsiElement getKW_ALIAS();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public PsiElement getOP_SCOLON();

    @NotNull
    public List<DLanguageAliasInitializer> getAliasInitializers();
}
