package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageVariableDeclaration extends PsiElement {
    @Nullable
    DLanguageAutoDeclaration getAutoDeclaration();

    @Nullable
    PsiElement getOP_SCOLON();

    @Nullable
    DLanguageType getType();

    @NotNull
    List<DLanguageDeclarator> getDeclarators();

    @NotNull
    List<PsiElement> getOP_COMMAs();

    @Nullable
    PsiElement getOP_EQ();

    @Nullable
    DLanguageFunctionBody getFunctionBody();
}
