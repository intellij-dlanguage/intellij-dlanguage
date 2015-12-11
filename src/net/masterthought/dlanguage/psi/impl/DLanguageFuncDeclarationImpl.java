// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import net.masterthought.dlanguage.psi.*;
import net.masterthought.dlanguage.psi.interfaces.DLanguageFuncDeclaration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_EQ;
import static net.masterthought.dlanguage.psi.DLanguageTypes.OP_SCOLON;

public class DLanguageFuncDeclarationImpl extends DNamedElementImpl implements DLanguageFuncDeclaration {

    public DLanguageFuncDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor) visitor).visitFuncDeclaration(this);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public DLanguageAutoFuncDeclaration getAutoFuncDeclaration() {
        return findChildByClass(DLanguageAutoFuncDeclaration.class);
    }

    @Override
    @Nullable
    public DLanguageBasicType getBasicType() {
        return findChildByClass(DLanguageBasicType.class);
    }

    @Override
    @Nullable
    public DLanguageFuncDeclarator getFuncDeclarator() {
        return findChildByClass(DLanguageFuncDeclarator.class);
    }

    @Override
    @Nullable
    public DLanguageFunctionBody getFunctionBody() {
        return findChildByClass(DLanguageFunctionBody.class);
    }

    @Override
    @Nullable
    public DLanguageStorageClasses getStorageClasses() {
        return findChildByClass(DLanguageStorageClasses.class);
    }

    @Override
    @Nullable
    public PsiElement getOpEq() {
        return findChildByType(OP_EQ);
    }

    @Override
    @Nullable
    public PsiElement getOpScolon() {
        return findChildByType(OP_SCOLON);
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
    public ItemPresentation getPresentation() {
        return DPsiImplUtil.getPresentation(this);
    }
}
