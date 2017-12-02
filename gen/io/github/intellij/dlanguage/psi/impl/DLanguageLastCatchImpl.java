package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_CATCH;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageLastCatch;
import io.github.intellij.dlanguage.psi.DLanguageStatementNoCaseNoDefault;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageLastCatchImpl extends ASTWrapperPsiElement implements DLanguageLastCatch {

    public DLanguageLastCatchImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitLastCatch(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_CATCH() {
        return findChildByType(KW_CATCH);
    }

    @Nullable
    public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStatementNoCaseNoDefault.class);
    }
}
