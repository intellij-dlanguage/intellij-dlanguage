package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_STATIC;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLangStaticForeachStatement;
import io.github.intellij.dlanguage.psi.DLanguageForeachStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * Created by francis on 1/17/2018.
 */
public class DLangStaticForeachStatementImpl extends ASTWrapperPsiElement implements
    DLangStaticForeachStatement {

    public DLangStaticForeachStatementImpl(@NotNull final ASTNode node) {
        super(node);
    }


    public void accept(@NotNull final DlangVisitor visitor) {
        visitor.visitStaticForeachStatement(this);
    }

    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    @Override
    public DLanguageForeachStatement getForeachStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageForeachStatement.class);
    }

    @NotNull
    @Override
    public PsiElement getKW_STATIC() {
        return findChildByType(KW_STATIC);
    }
}
