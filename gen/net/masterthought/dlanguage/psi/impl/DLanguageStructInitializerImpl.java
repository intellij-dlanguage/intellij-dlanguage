package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageStructInitializer;
import net.masterthought.dlanguage.psi.DLanguageStructMemberInitializers;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_BRACES_LEFT;
import static net.masterthought.dlanguage.psi.DlangTypes.OP_BRACES_RIGHT;


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
        return findChildByType(OP_BRACES_RIGHT);
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(OP_BRACES_LEFT);
    }

}
