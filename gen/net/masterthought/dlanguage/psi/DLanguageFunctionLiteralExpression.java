package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageFunctionLiteralExpression extends PsiElement {
    @Nullable
    public DLanguageType getType();

    @Nullable
    public PsiElement getKW_FUNCTION();

    @Nullable
    public PsiElement getKW_DELEGATE();

    @Nullable
    public DLanguageParameters getParameters();

    @NotNull
    public List<DLanguageFunctionAttribute> getFunctionAttributes();

    @Nullable
    public DLanguageFunctionBody getFunctionBody();

    @Nullable
    public DLanguageIdentifier getIdentifier();
}
