package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguagePrimaryExpression extends PsiElement {
    @Nullable
    DLanguageType getType();

    @Nullable
    DLanguageArguments getArguments();

    @Nullable
    DLanguageFunctionLiteralExpression getFunctionLiteralExpression();

    @Nullable
    DLanguageTypeofExpression getTypeofExpression();

    @Nullable
    DLanguageTypeidExpression getTypeidExpression();

    @Nullable
    DLanguageVector getVector();

    @Nullable
    DLanguageAssocArrayLiteral getAssocArrayLiteral();

    @Nullable
    DLanguageArrayLiteral getArrayLiteral();

    @Nullable
    DLanguageExpression getExpression();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    DLanguageIsExpression getIsExpression();

    @Nullable
    DLanguageLambdaExpression getLambdaExpression();

    @Nullable
    DLanguageTraitsExpression getTraitsExpression();

    @Nullable
    DLanguageMixinExpression getMixinExpression();

    @Nullable
    DLanguageImportExpression getImportExpression();

    @Nullable
    PsiElement getKW_SUPER();

    @Nullable
    PsiElement getKW_THIS();

    @Nullable
    PsiElement getOP_DOLLAR();

    @Nullable
    PsiElement getKW_TRUE();

    @Nullable
    PsiElement getKW_FALSE();

    @Nullable
    PsiElement getKW___DATE__();

    @Nullable
    PsiElement getKW___EOF__();

    @Nullable
    PsiElement getKW___FILE__();

    @Nullable
    PsiElement getKW___FILE_FULL_PATH__();

    @Nullable
    PsiElement getKW___FUNCTION__();

    @Nullable
    PsiElement getKW___GSHARED();

    @Nullable
    PsiElement getKW___LINE__();

    @Nullable
    PsiElement getKW___MODULE__();

    @Nullable
    PsiElement getKW___PARAMETERS();

    @Nullable
    PsiElement getKW___PRETTY_FUNCTION__();

    @Nullable
    PsiElement getKW___TIME__();

    @Nullable
    PsiElement getKW___TIMESTAMP__();

    @Nullable
    PsiElement getKW___TRAITS();

    @Nullable
    PsiElement getKW___VECTOR();

    @Nullable
    PsiElement getKW___VENDOR__();

    @Nullable
    PsiElement getKW___VERSION__();

    @Nullable
    PsiElement getINTEGER_LITERAL();

    @Nullable
    PsiElement getFLOAT_LITERAL();

    @NotNull
    List<PsiElement> getDOUBLE_QUOTED_STRINGs();

    @Nullable
    PsiElement getCHARACTER_LITERAL();

    @Nullable
    DLanguageIdentifierOrTemplateInstance getIdentifierOrTemplateInstance();

    @Nullable
    PsiElement getOP_DOT();

    @Nullable
    DLanguageTypeConstructor getTypeConstructor();
}
