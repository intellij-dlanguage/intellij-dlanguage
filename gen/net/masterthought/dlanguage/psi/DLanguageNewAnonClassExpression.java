package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


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
