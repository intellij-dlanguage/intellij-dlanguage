package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageCompileConditionImpl extends ASTWrapperPsiElement implements DLanguageCompileCondition {
    public DLanguageCompileConditionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitCompileCondition(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageVersionCondition getVersionCondition() {
        return PsiTreeUtil.getChildOfType(this, DLanguageVersionCondition.class);
    }

    @Nullable
    public DLanguageDebugCondition getDebugCondition() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDebugCondition.class);
    }

    @Nullable
    public DLanguageStaticIfCondition getStaticIfCondition() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStaticIfCondition.class);
    }
}
