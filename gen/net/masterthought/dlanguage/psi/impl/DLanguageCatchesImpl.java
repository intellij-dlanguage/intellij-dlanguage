package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageCatch;
import net.masterthought.dlanguage.psi.DLanguageCatches;
import net.masterthought.dlanguage.psi.DLanguageLastCatch;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageCatchesImpl extends ASTWrapperPsiElement implements DLanguageCatches {
    public DLanguageCatchesImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitCatches(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageLastCatch getLastCatch() {
        return PsiTreeUtil.getChildOfType(this, DLanguageLastCatch.class);
    }

    @Nullable
    public DLanguageCatch getCatch() {
        return PsiTreeUtil.getChildOfType(this, DLanguageCatch.class);
    }
}
