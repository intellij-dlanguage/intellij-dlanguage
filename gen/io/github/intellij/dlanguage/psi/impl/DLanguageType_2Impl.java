package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_DOT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_RIGHT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierOrTemplateChain;
import io.github.intellij.dlanguage.psi.DLanguageSymbol;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.DLanguageType_2;
import io.github.intellij.dlanguage.psi.DLanguageTypeofExpression;
import io.github.intellij.dlanguage.psi.DLanguageVector;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageType_2Impl extends ASTWrapperPsiElement implements DLanguageType_2 {

    public DLanguageType_2Impl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitType_2(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageSymbol getSymbol() {
        return PsiTreeUtil.getChildOfType(this, DLanguageSymbol.class);
    }

    @Nullable
    public DLanguageTypeofExpression getTypeofExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeofExpression.class);
    }

    @Nullable
    public DLanguageTypeConstructor getTypeConstructor() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTypeConstructor.class);
    }

    @Nullable
    public DLanguageVector getVector() {
        return PsiTreeUtil.getChildOfType(this, DLanguageVector.class);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierOrTemplateChain.class);
    }

    @Nullable
    public PsiElement getOP_DOT() {
        return findChildByType(OP_DOT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

}
