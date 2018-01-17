

package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_BREAK;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SCOLON;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageBreakStatement;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageBreakStatementImpl extends ASTWrapperPsiElement implements
    DLanguageBreakStatement {

    public DLanguageBreakStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitBreakStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_BREAK() {
        return findChildByType(KW_BREAK);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

}
