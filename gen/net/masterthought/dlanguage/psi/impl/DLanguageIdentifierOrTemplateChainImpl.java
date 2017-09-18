package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageIdentifierOrTemplateChain;
import net.masterthought.dlanguage.psi.DLanguageIdentifierOrTemplateInstance;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_DOT;


public class DLanguageIdentifierOrTemplateChainImpl extends ASTWrapperPsiElement implements DLanguageIdentifierOrTemplateChain {
    public DLanguageIdentifierOrTemplateChainImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitIdentifierOrTemplateChain(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @NotNull
    public List<PsiElement> getOP_DOTs() {
        return findChildrenByType(OP_DOT);
    }

    @NotNull
    public List<DLanguageIdentifierOrTemplateInstance> getIdentifierOrTemplateInstances() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageIdentifierOrTemplateInstance.class);
    }
}
