package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageNewAnonClassExpression extends PsiElement {

    @Nullable
    public PsiElement getKW_NEW();

    @Nullable
    public PsiElement getKW_CLASS();

    @NotNull
    public List<DLanguageArguments> getArgumentss();

    @Nullable
    public DLanguageBaseClassList getBaseClassList();

    @Nullable
    public DLanguageStructBody getStructBody();
}
