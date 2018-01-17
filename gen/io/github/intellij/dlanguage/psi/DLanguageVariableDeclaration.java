package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangDeclarator;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageVariableDeclaration extends PsiElement {

    @Nullable
    DLanguageAutoDeclaration getAutoDeclaration();

    @Nullable
    PsiElement getOP_SCOLON();

    @Nullable
    DLanguageType getType();

    @NotNull
    List<DlangDeclarator> getDeclarators();

    @NotNull
    List<PsiElement> getOP_COMMAs();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageFunctionBody getFunctionBody();

    @NotNull
    List<DLanguageStorageClass> getStorageClasss();
}
