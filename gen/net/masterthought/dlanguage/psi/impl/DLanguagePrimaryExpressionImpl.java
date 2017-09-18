package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.*;


public class DLanguagePrimaryExpressionImpl extends ASTWrapperPsiElement implements DLanguagePrimaryExpression {
    public DLanguagePrimaryExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitPrimaryExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public DLanguageArguments getArguments() {
        return PsiTreeUtil.getChildOfType(this, DLanguageArguments.class);
    }

    @Nullable
    public DLanguageFunctionLiteralExpression getFunctionLiteralExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionLiteralExpression.class);
    }

    @Nullable
    public DLanguageTypeofExpression getTypeofExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeofExpression.class);
    }

    @Nullable
    public DLanguageTypeidExpression getTypeidExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeidExpression.class);
    }

    @Nullable
    public DLanguageVector getVector() {
        return PsiTreeUtil.getChildOfType(this, DLanguageVector.class);
    }

    @Nullable
    public DLanguageAssocArrayLiteral getAssocArrayLiteral() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssocArrayLiteral.class);
    }

    @Nullable
    public DLanguageArrayLiteral getArrayLiteral() {
        return PsiTreeUtil.getChildOfType(this, DLanguageArrayLiteral.class);
    }

    @Nullable
    public DLanguageExpression getExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageExpression.class);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Nullable
    public DLanguageIsExpression getIsExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIsExpression.class);
    }

    @Nullable
    public DLanguageLambdaExpression getLambdaExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageLambdaExpression.class);
    }

    @Nullable
    public DLanguageTraitsExpression getTraitsExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTraitsExpression.class);
    }

    @Nullable
    public DLanguageMixinExpression getMixinExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageMixinExpression.class);
    }

    @Nullable
    public DLanguageImportExpression getImportExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageImportExpression.class);
    }

    @Nullable
    public PsiElement getKW_SUPER() {
        return findChildByType(KW_SUPER);
    }

    @Nullable
    public PsiElement getKW_THIS() {
        return findChildByType(KW_THIS);
    }

    @Nullable
    public PsiElement getOP_DOLLAR() {
        return findChildByType(OP_DOLLAR);
    }

    @Nullable
    public PsiElement getKW_TRUE() {
        return findChildByType(KW_TRUE);
    }

    @Nullable
    public PsiElement getKW_FALSE() {
        return findChildByType(KW_FALSE);
    }

    @Nullable
    public PsiElement getKW___DATE__() {
        return findChildByType(KW___DATE__);
    }

    @Nullable
    public PsiElement getKW___EOF__() {
        return findChildByType(KW___EOF__);
    }

    @Nullable
    public PsiElement getKW___FILE__() {
        return findChildByType(KW___FILE__);
    }

    @Nullable
    public PsiElement getKW___FILE_FULL_PATH__() {
        return findChildByType(KW___FILE_FULL_PATH__);
    }

    @Nullable
    public PsiElement getKW___FUNCTION__() {
        return findChildByType(KW___FUNCTION__);
    }

    @Nullable
    public PsiElement getKW___GSHARED() {
        return findChildByType(KW___GSHARED);
    }

    @Nullable
    public PsiElement getKW___LINE__() {
        return findChildByType(KW___LINE__);
    }

    @Nullable
    public PsiElement getKW___MODULE__() {
        return findChildByType(KW___MODULE__);
    }

    @Nullable
    public PsiElement getKW___PARAMETERS() {
        return findChildByType(KW___PARAMETERS);
    }

    @Nullable
    public PsiElement getKW___PRETTY_FUNCTION__() {
        return findChildByType(KW___PRETTY_FUNCTION__);
    }

    @Nullable
    public PsiElement getKW___TIME__() {
        return findChildByType(KW___TIME__);
    }

    @Nullable
    public PsiElement getKW___TIMESTAMP__() {
        return findChildByType(KW___TIMESTAMP__);
    }

    @Nullable
    public PsiElement getKW___TRAITS() {
        return findChildByType(KW___TRAITS);
    }

    @Nullable
    public PsiElement getKW___VECTOR() {
        return findChildByType(KW___VECTOR);
    }

    @Nullable
    public PsiElement getKW___VENDOR__() {
        return findChildByType(KW___VENDOR__);
    }

    @Nullable
    public PsiElement getKW___VERSION__() {
        return findChildByType(KW___VERSION__);
    }

    @Nullable
    public PsiElement getINTEGER_LITERAL() {
        return findChildByType(INTEGER_LITERAL);
    }

    @Nullable
    public PsiElement getFLOAT_LITERAL() {
        return findChildByType(FLOAT_LITERAL);
    }

    @NotNull
    public List<PsiElement> getDOUBLE_QUOTED_STRINGs() {
        return findChildrenByType(DOUBLE_QUOTED_STRING);
    }

    @Nullable
    public PsiElement getCHARACTER_LITERAL() {
        return findChildByType(CHARACTER_LITERAL);
    }

    @Nullable
    public DLanguageIdentifierOrTemplateInstance getIdentifierOrTemplateInstance() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierOrTemplateInstance.class);
    }

    @Nullable
    public PsiElement getOP_DOT() {
        return findChildByType(OP_DOT);
    }

    @Nullable
    public DLanguageTypeConstructor getTypeConstructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeConstructor.class);
    }
}
