package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageCatches;
import io.github.intellij.dlanguage.psi.DLanguageLastCatch;
import io.github.intellij.dlanguage.psi.named.DlangCatch;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageCatchesImpl extends ASTWrapperPsiElement implements DLanguageCatches {

    public DLanguageCatchesImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitCatches(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageLastCatch getLastCatch() {
        return PsiTreeUtil.getChildOfType(this, DLanguageLastCatch.class);
    }

    @Nullable
    public DlangCatch getCatch() {
        return PsiTreeUtil.getChildOfType(this, DlangCatch.class);
    }
}
