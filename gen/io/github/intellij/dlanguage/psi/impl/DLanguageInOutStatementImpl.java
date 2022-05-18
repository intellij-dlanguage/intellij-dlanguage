package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageInOutStatement;
import io.github.intellij.dlanguage.psi.DLanguageInStatement;
import io.github.intellij.dlanguage.psi.DLanguageOutStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DLanguageInOutStatementImpl extends ASTWrapperPsiElement implements
    DLanguageInOutStatement {

    public DLanguageInOutStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitInOutStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageInStatement getInStatement() {
        return PsiTreeUtil.findChildOfType(this, DLanguageInStatement.class);
    }

    @Nullable
    public DLanguageOutStatement getOutStatement() {
        return PsiTreeUtil.findChildOfType(this, DLanguageOutStatement.class);
    }
}
