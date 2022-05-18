package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageBlockStatement;
import io.github.intellij.dlanguage.psi.DLanguageFunctionContract;
import io.github.intellij.dlanguage.psi.DLanguageSpecifiedFunctionBody;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_DO;

public class DLanguageSpecifiedFunctionBodyImpl extends ASTWrapperPsiElement implements
    DLanguageSpecifiedFunctionBody {

    public DLanguageSpecifiedFunctionBodyImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitSpecifiedFunctionBody(this);
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

    @Nullable
    public PsiElement getKW_DO() {
        return findChildByType(KW_DO);
    }

    @NotNull
    public DLanguageBlockStatement getBlockStatement() {
        return PsiTreeUtil.findChildOfType(this, DLanguageBlockStatement.class);
    }
}
