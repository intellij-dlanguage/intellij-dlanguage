package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageStructInitializer;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import io.github.intellij.dlanguage.psi.DLanguageStructInitializer;
import io.github.intellij.dlanguage.psi.DLanguageStructMemberInitializers;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_RIGHT;


public class DLanguageStructInitializerImpl extends ASTWrapperPsiElement implements DLanguageStructInitializer {
    public DLanguageStructInitializerImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStructInitializer(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<DLanguageStructMemberInitializers> getStructMemberInitializerss() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageStructMemberInitializers.class);
    }

    @Nullable
    public PsiElement getOP_BRACES_RIGHT() {
        return findChildByType(DlangTypes.OP_BRACES_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(DlangTypes.OP_BRACES_LEFT);
    }

}
