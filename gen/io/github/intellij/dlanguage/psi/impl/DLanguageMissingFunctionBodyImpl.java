package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageFunctionContract;
import io.github.intellij.dlanguage.psi.DLanguageMissingFunctionBody;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.SEMICOLON;

public class DLanguageMissingFunctionBodyImpl extends ASTWrapperPsiElement implements
    DLanguageMissingFunctionBody {

    public DLanguageMissingFunctionBodyImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitMissingFunctionBody(this);
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
    public PsiElement getSEMICOLON() {
        return findChildByType(SEMICOLON);
    }
}
