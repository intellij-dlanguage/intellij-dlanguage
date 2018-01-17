package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


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
    public DlangIdentifier getIdentifier();
}
