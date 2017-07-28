package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageLambdaExpression extends PsiElement {
    @Nullable
    public DLanguageIdentifier getIdentifier();

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
