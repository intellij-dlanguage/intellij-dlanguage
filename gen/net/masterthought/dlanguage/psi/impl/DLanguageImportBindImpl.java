package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DlangIdentifier;
import net.masterthought.dlanguage.psi.DLanguageImportBind;
import net.masterthought.dlanguage.psi.DLanguageNamedImportBind;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageImportBindImpl extends ASTWrapperPsiElement implements DLanguageImportBind {
    public DLanguageImportBindImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitImportBind(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public DLanguageNamedImportBind getNamedImportBind() {
        return PsiTreeUtil.getChildOfType(this, DLanguageNamedImportBind.class);
    }
}
