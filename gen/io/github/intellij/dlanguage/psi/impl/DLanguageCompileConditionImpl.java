package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageCompileCondition;
import io.github.intellij.dlanguage.psi.DLanguageDebugCondition;
import io.github.intellij.dlanguage.psi.DLanguageStaticIfCondition;
import io.github.intellij.dlanguage.psi.DLanguageVersionCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageCompileConditionImpl extends ASTWrapperPsiElement implements
    DLanguageCompileCondition {

    public DLanguageCompileConditionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitCompileCondition(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) {
            accept((DLanguageVisitor) visitor);
        } else {
            super.accept(visitor);
        }
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
