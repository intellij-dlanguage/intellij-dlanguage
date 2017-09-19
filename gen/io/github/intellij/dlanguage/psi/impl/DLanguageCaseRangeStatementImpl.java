package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.DLanguageAssignExpression;
import io.github.intellij.dlanguage.psi.DLanguageCaseRangeStatement;
import io.github.intellij.dlanguage.psi.DLanguageDeclarationsAndStatements;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageCaseRangeStatementImpl extends ASTWrapperPsiElement implements DLanguageCaseRangeStatement {
    public DLanguageCaseRangeStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitCaseRangeStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<PsiElement> getKW_CASEs() {
        return findChildrenByType(DlangTypes.KW_CASE);
    }

    @Nullable
    public PsiElement getOP_TRIPLEDOT() {
        return findChildByType(DlangTypes.OP_TRIPLEDOT);
    }

    @NotNull
    public List<PsiElement> getOP_COLONs() {
        return findChildrenByType(DlangTypes.OP_COLON);
    }

    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
    }

    @Nullable
    public DLanguageDeclarationsAndStatements getDeclarationsAndStatements() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDeclarationsAndStatements.class);
    }
}
