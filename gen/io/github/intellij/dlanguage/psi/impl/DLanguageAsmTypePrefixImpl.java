package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmTypePrefix;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public class DLanguageAsmTypePrefixImpl extends ASTWrapperPsiElement implements
    DLanguageAsmTypePrefix {

    public DLanguageAsmTypePrefixImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmTypePrefix(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @NotNull
    public List<DlangIdentifier> getIdentifiers() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DlangIdentifier.class);
    }
}
