package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.psi.DVisitor;
import net.masterthought.dlanguage.psi.interfaces.DRefIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DRefIdentifierImpl extends DNamedElementImpl implements DRefIdentifier {

    public DRefIdentifierImpl(ASTNode node) {
        super(node);
    }

    @Nullable
    public PsiElement getNameIdentifier() {
        return DPsiImplUtil.getNameIdentifier(this);
    }

    @NotNull
    public String getName() {
        return DPsiImplUtil.getName(this);
    }

    @Nullable
    public PsiElement setName(String newName) {
        return DPsiImplUtil.setName(this, newName);
    }

    @NotNull
    public PsiReference getReference() {
        return DPsiImplUtil.getReference(this);
    }

    @NotNull
         public ItemPresentation getPresentation() {
           return DPsiImplUtil.getPresentation(this);
         }

}
