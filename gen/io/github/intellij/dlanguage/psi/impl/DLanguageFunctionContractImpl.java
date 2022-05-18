package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageFunctionContract;
import io.github.intellij.dlanguage.psi.DLanguageInOutContractExpression;
import io.github.intellij.dlanguage.psi.DLanguageInOutStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_LEFT;

public class DLanguageFunctionContractImpl extends ASTWrapperPsiElement implements
    DLanguageFunctionContract {

    public DLanguageFunctionContractImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitFunctionContract(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(OP_BRACES_LEFT);
    }

    @Nullable
    public DLanguageInOutStatement getInOutStatement() {
        return PsiTreeUtil.findChildOfType(this, DLanguageInOutStatement.class);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    public DLanguageInOutContractExpression getInOutContractExpression() {
        return PsiTreeUtil.findChildOfType(this, DLanguageInOutContractExpression.class);
    }

}
