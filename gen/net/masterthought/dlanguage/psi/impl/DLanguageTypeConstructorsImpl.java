package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageTypeConstructor;
import net.masterthought.dlanguage.psi.DLanguageTypeConstructors;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class DLanguageTypeConstructorsImpl extends ASTWrapperPsiElement implements DLanguageTypeConstructors {
    public DLanguageTypeConstructorsImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitTypeConstructors(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<DLanguageTypeConstructor> getTypeConstructors() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTypeConstructor.class);
    }
}
