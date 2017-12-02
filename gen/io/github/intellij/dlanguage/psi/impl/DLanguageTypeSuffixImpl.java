package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_DELEGATE;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FUNCTION;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_ASTERISK;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACKET_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACKET_RIGHT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_TRIPLEDOT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DLanguageMemberFunctionAttribute;
import io.github.intellij.dlanguage.psi.DLanguageParameters;
import io.github.intellij.dlanguage.psi.DLanguageTypeSuffix;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageTypeSuffixImpl extends ASTWrapperPsiElement implements DLanguageTypeSuffix {

    public DLanguageTypeSuffixImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTypeSuffix(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public List<DLanguageAssignExpression> getAssignExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAssignExpression.class);
    }

    @Nullable
    public PsiElement getOP_TRIPLEDOT() {
        return findChildByType(OP_TRIPLEDOT);
    }

    @Nullable
    public PsiElement getKW_FUNCTION() {
        return findChildByType(KW_FUNCTION);
    }

    @Nullable
    public PsiElement getKW_DELEGATE() {
        return findChildByType(KW_DELEGATE);
    }

    @Nullable
    public PsiElement getOP_ASTERISK() {
        return findChildByType(OP_ASTERISK);
    }

    @NotNull
    public List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageMemberFunctionAttribute.class);
    }

    @Nullable
    public DLanguageParameters getParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageParameters.class);
    }

    @Nullable
    public PsiElement getOP_BRACKET_LEFT() {
        return findChildByType(OP_BRACKET_LEFT);
    }

    @Nullable
    public PsiElement getOP_BRACKET_RIGHT() {
        return findChildByType(OP_BRACKET_RIGHT);
    }

}
