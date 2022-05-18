package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageType_2 extends PsiElement {

    @Nullable
    public DLanguageTypeofExpression getTypeofExpression();

    @Nullable
    public DLanguageTypeConstructor getTypeConstructor();

    @Nullable
    public DLanguageTraitsExpression getTraitsExpression();

    @Nullable
    public DLanguageMixinExpression getMixinExpression();

    @Nullable
    public DLanguageVector getVector();

    @Nullable
    public DLanguageType getType();

    @Nullable
    public DLanguageTypeIdentifierPart getTypeIdentifierPart();

    @Nullable
    public PsiElement getOP_DOT();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

}
