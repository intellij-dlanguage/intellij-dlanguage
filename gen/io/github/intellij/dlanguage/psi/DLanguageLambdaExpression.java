package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageLambdaExpression extends PsiElement {

    @Nullable
    public DlangIdentifier getIdentifier();

    @Nullable
    public PsiElement getKW_FUNCTION();

    @Nullable
    public PsiElement getKW_DELEGATE();

    @Nullable
    public PsiElement getOP_LAMBDA_ARROW();

    @Nullable
    public DLanguageAssignExpression getAssignExpression();

    @Nullable
    public DLanguageParameters getParameters();

    @NotNull
    public List<DLanguageFunctionAttribute> getFunctionAttributes();
}
