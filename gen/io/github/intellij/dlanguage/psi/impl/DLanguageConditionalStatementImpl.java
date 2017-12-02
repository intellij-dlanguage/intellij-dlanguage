package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_ELSE;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_RIGHT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageCompileCondition;
import io.github.intellij.dlanguage.psi.DLanguageConditionalStatement;
import io.github.intellij.dlanguage.psi.DLanguageDeclarationOrStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageConditionalStatementImpl extends ASTWrapperPsiElement implements
    DLanguageConditionalStatement {

    public DLanguageConditionalStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitConditionalStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageCompileCondition getCompileCondition() {
        return PsiTreeUtil.getChildOfType(this, DLanguageCompileCondition.class);
    }

    @NotNull
    public List<DLanguageDeclarationOrStatement> getDeclarationOrStatements() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageDeclarationOrStatement.class);
    }

    @Nullable
    public PsiElement getKW_ELSE() {
        return findChildByType(KW_ELSE);
    }

    @Nullable
    public PsiElement getOP_BRACES_RIGHT() {
        return findChildByType(OP_BRACES_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(OP_BRACES_LEFT);
    }

}
