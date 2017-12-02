package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_FINAL;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageFinalSwitchStatement;
import io.github.intellij.dlanguage.psi.DLanguageSwitchStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageFinalSwitchStatementImpl extends ASTWrapperPsiElement implements
    DLanguageFinalSwitchStatement {

    public DLanguageFinalSwitchStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitFinalSwitchStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_FINAL() {
        return findChildByType(KW_FINAL);
    }

    @Nullable
    public DLanguageSwitchStatement getSwitchStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageSwitchStatement.class);
    }
}
