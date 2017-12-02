package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.CHARACTER_LITERAL;
import static io.github.intellij.dlanguage.psi.DlangTypes.DOUBLE_QUOTED_STRING;
import static io.github.intellij.dlanguage.psi.DlangTypes.FLOAT_LITERAL;
import static io.github.intellij.dlanguage.psi.DlangTypes.INTEGER_LITERAL;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FALSE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_SUPER;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_THIS;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_TRUE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___DATE__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___EOF__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___FILE_FULL_PATH__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___FILE__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___FUNCTION__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___GSHARED;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___LINE__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___MODULE__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___PARAMETERS;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___PRETTY_FUNCTION__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___TIMESTAMP__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___TIME__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___TRAITS;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___VECTOR;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___VENDOR__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___VERSION__;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_DOLLAR;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_DOT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_RIGHT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageArguments;
import io.github.intellij.dlanguage.psi.DLanguageArrayLiteral;
import io.github.intellij.dlanguage.psi.DLanguageAssocArrayLiteral;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DLanguageFunctionLiteralExpression;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierOrTemplateInstance;
import io.github.intellij.dlanguage.psi.DLanguageImportExpression;
import io.github.intellij.dlanguage.psi.DLanguageIsExpression;
import io.github.intellij.dlanguage.psi.DLanguageLambdaExpression;
import io.github.intellij.dlanguage.psi.DLanguageMixinExpression;
import io.github.intellij.dlanguage.psi.DLanguagePrimaryExpression;
import io.github.intellij.dlanguage.psi.DLanguageTraitsExpression;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.DLanguageTypeidExpression;
import io.github.intellij.dlanguage.psi.DLanguageTypeofExpression;
import io.github.intellij.dlanguage.psi.DLanguageVector;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguagePrimaryExpressionImpl extends ASTWrapperPsiElement implements
    DLanguagePrimaryExpression {

    public DLanguagePrimaryExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitPrimaryExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
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
