package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageInterfaceDeclaration;
import net.masterthought.dlanguage.psi.DlangInterfaceOrClass;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.KW_INTERFACE;


public class DLanguageInterfaceDeclarationImpl extends ASTWrapperPsiElement implements DLanguageInterfaceDeclaration {
    public DLanguageInterfaceDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitInterfaceDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
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
