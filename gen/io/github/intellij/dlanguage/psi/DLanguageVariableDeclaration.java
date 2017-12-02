package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageVariableDeclaration extends PsiElement {

    @Nullable
    public DLanguageAutoDeclaration getAutoDeclaration();

    @Nullable
    public PsiElement getOP_SCOLON();

    @Nullable
    public DLanguageType getType();

    @NotNull
    public List<DLanguageDeclarator> getDeclarators();

    @NotNull
    public List<PsiElement> getOP_COMMAs();

    @Nullable
    public PsiElement getOP_EQ();

    @Nullable
    public DLanguageFunctionBody getFunctionBody();

    @NotNull
    public List<DLanguageStorageClass> getStorageClasss();
}
