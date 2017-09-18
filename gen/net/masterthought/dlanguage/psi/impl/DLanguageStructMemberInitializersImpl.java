package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageStructMemberInitializer;
import net.masterthought.dlanguage.psi.DLanguageStructMemberInitializers;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_COMMA;


public class DLanguageStructMemberInitializersImpl extends ASTWrapperPsiElement implements DLanguageStructMemberInitializers {
    public DLanguageStructMemberInitializersImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStructMemberInitializers(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<DLanguageStructMemberInitializer> getStructMemberInitializers() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageStructMemberInitializer.class);
    }

    @NotNull
    public List<PsiElement> getOP_COMMAs() {
        return findChildrenByType(OP_COMMA);
    }

}
