package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_INTERFACE;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageInterfaceDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceOrClass;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageInterfaceDeclarationImpl extends ASTWrapperPsiElement implements
    DLanguageInterfaceDeclaration {

    public DLanguageInterfaceDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitInterfaceDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_INTERFACE() {
        return findChildByType(KW_INTERFACE);
    }

    @Nullable
    public DlangInterfaceOrClass getInterfaceOrClass() {
        return PsiTreeUtil.getChildOfType(this, DlangInterfaceOrClass.class);
    }
}
