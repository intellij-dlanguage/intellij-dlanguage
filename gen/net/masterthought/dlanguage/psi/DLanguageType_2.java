package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageType_2 extends PsiElement {
    @Nullable
    DLanguageSymbol getSymbol();

    @Nullable
    DLanguageTypeofExpression getTypeofExpression();

    @Nullable
    DLanguageTypeConstructor getTypeConstructor();

    @Nullable
    DLanguageVector getVector();

    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain();

    @Nullable
    PsiElement getOP_DOT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    PsiElement getOP_PAR_LEFT();

}
