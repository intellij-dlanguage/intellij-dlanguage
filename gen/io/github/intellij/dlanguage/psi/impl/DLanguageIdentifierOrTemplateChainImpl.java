

package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_DOT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierOrTemplateChain;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierOrTemplateInstance;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public class DLanguageIdentifierOrTemplateChainImpl extends ASTWrapperPsiElement implements
    DLanguageIdentifierOrTemplateChain {

    public DLanguageIdentifierOrTemplateChainImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitIdentifierOrTemplateChain(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public List<PsiElement> getOP_DOTs() {
        return findChildrenByType(OP_DOT);
    }

    @NotNull
    public List<DLanguageIdentifierOrTemplateInstance> getIdentifierOrTemplateInstances() {
        return PsiTreeUtil
            .getChildrenOfTypeAsList(this, DLanguageIdentifierOrTemplateInstance.class);
    }
}
