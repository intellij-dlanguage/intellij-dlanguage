package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW___FILE__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___FUNCTION__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___LINE__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___MODULE__;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW___PRETTY_FUNCTION__;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_EQ;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DLanguageTemplateValueParameterDefault;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageTemplateValueParameterDefaultImpl extends ASTWrapperPsiElement implements
    DLanguageTemplateValueParameterDefault {

    public DLanguageTemplateValueParameterDefaultImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateValueParameterDefault(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getOP_EQ() {
        return findChildByType(OP_EQ);
    }

    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
    }

    @Nullable
    public PsiElement getKW___FILE__() {
        return findChildByType(KW___FILE__);
    }

    @Nullable
    public PsiElement getKW___FUNCTION__() {
        return findChildByType(KW___FUNCTION__);
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
    public PsiElement getKW___PRETTY_FUNCTION__() {
        return findChildByType(KW___PRETTY_FUNCTION__);
    }

}
