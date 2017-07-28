package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageVersionCondition extends PsiElement {
    @Nullable
    public PsiElement getKW_VERSION();

    @Nullable
    public PsiElement getKW_UNITTEST();

    @Nullable
    public PsiElement getKW_ASSERT();

    @Nullable
    public PsiElement getINTEGER_LITERAL();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
