package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructor;
import io.github.intellij.dlanguage.psi.DLanguageTypeConstructors;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public class DLanguageTypeConstructorsImpl extends ASTWrapperPsiElement implements
    DLanguageTypeConstructors {

    public DLanguageTypeConstructorsImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTypeConstructors(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public List<DLanguageTypeConstructor> getTypeConstructors() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTypeConstructor.class);
    }
}
