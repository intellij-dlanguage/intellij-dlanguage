package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_CLASS;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageClassDeclaration;
import io.github.intellij.dlanguage.psi.named.DlangInterfaceOrClass;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageClassDeclarationImpl extends ASTWrapperPsiElement implements
    DLanguageClassDeclaration {

    public DLanguageClassDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitClassDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_CLASS() {
        return findChildByType(KW_CLASS);
    }

    @Nullable
    public DlangInterfaceOrClass getInterfaceOrClass() {
        return PsiTreeUtil.getChildOfType(this, DlangInterfaceOrClass.class);
    }
}
