package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SCOLON;

public class DLanguagePragmaStatementImpl extends ASTWrapperPsiElement implements
    DLanguagePragmaStatement {

    public DLanguagePragmaStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitPragmaStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public DLanguagePragmaExpression getPragmaExpression() {
        return PsiTreeUtil.findChildOfType(this, DLanguagePragmaExpression.class);
    }

    @Nullable
    public DLanguageBlockStatement getBlockStatement() {
        return PsiTreeUtil.findChildOfType(this, DLanguageBlockStatement.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

    @Nullable
    public DLanguageStatement getStatement() {
        return PsiTreeUtil.findChildOfType(this, DLanguageStatement.class);
    }
}
