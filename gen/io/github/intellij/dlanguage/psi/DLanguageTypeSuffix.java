package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageTypeSuffix extends PsiElement {

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions();

    @Nullable
    public PsiElement getOP_TRIPLEDOT();

    @Nullable
    public PsiElement getKW_FUNCTION();

    @Nullable
    public PsiElement getKW_DELEGATE();

    @Nullable
    public PsiElement getOP_ASTERISK();

    @NotNull
    public List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes();

    @Nullable
    public DLanguageParameters getParameters();

    @Nullable
    public PsiElement getOP_BRACKET_LEFT();

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT();

}
