package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageCaseRangeStatement;
import io.github.intellij.dlanguage.psi.DLanguageCaseStatement;
import io.github.intellij.dlanguage.psi.DLanguageDefaultStatement;
import io.github.intellij.dlanguage.psi.DLanguageStatement;
import io.github.intellij.dlanguage.psi.DLanguageStatementNoCaseNoDefault;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageStatementImpl extends ASTWrapperPsiElement implements DLanguageStatement {

    public DLanguageStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageDefaultStatement getDefaultStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDefaultStatement.class);
    }

    @Nullable
    public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStatementNoCaseNoDefault.class);
    }

    @Nullable
    public DLanguageCaseStatement getCaseStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageCaseStatement.class);
    }

    @Nullable
    public DLanguageCaseRangeStatement getCaseRangeStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageCaseRangeStatement.class);
    }
}
