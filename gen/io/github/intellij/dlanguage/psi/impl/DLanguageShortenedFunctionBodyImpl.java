package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageExpression;
import io.github.intellij.dlanguage.psi.DLanguageFunctionContract;
import io.github.intellij.dlanguage.psi.DLanguageShortenedFunctionBody;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_LAMBDA_ARROW;
import static io.github.intellij.dlanguage.psi.DlangTypes.SEMICOLON;

public class DLanguageShortenedFunctionBodyImpl extends ASTWrapperPsiElement implements
    DLanguageShortenedFunctionBody {

    public DLanguageShortenedFunctionBodyImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitShortenedFunctionBody(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public List<DLanguageFunctionContract> getFunctionContracts() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageFunctionContract.class);
    }

    @NotNull
    public PsiElement getOP_LAMBDA_ARROW() {
        return findChildByType(OP_LAMBDA_ARROW);
    }

    @NotNull
    public DLanguageExpression getExpression() {
        return PsiTreeUtil.findChildOfType(this, DLanguageExpression.class);
    }

    @NotNull
    public PsiElement getSEMICOLON() {
        return findChildByType(SEMICOLON);
    }
}
