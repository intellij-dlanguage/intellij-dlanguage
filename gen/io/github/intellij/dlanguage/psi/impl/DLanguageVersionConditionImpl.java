package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.INTEGER_LITERAL;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_ASSERT;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_UNITTEST;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_VERSION;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_RIGHT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageVersionCondition;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageVersionConditionImpl extends ASTWrapperPsiElement implements
    DLanguageVersionCondition {

    public DLanguageVersionConditionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitVersionCondition(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_VERSION() {
        return findChildByType(KW_VERSION);
    }

    @Nullable
    public PsiElement getKW_UNITTEST() {
        return findChildByType(KW_UNITTEST);
    }

    @Nullable
    public PsiElement getKW_ASSERT() {
        return findChildByType(KW_ASSERT);
    }

    @Nullable
    public PsiElement getINTEGER_LITERAL() {
        return findChildByType(INTEGER_LITERAL);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
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
