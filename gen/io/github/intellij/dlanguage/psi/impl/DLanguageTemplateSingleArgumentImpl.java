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

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageTemplateSingleArgument;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageTemplateSingleArgumentImpl extends ASTWrapperPsiElement implements
    DLanguageTemplateSingleArgument {

    public DLanguageTemplateSingleArgumentImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateSingleArgument(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
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

    @Nullable
    public PsiElement getDOUBLE_QUOTED_STRING() {
        return findChildByType(DOUBLE_QUOTED_STRING);
    }

    @Nullable
    public PsiElement getCHARACTER_LITERAL() {
        return findChildByType(CHARACTER_LITERAL);
    }

}
