// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.DLanguageSymbol;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageSymbolImpl extends DNamedElementImpl implements DLanguageSymbol {

  public DLanguageSymbolImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitSymbol(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageSymbolTail getSymbolTail() {
    return findNotNullChildByClass(DLanguageSymbolTail.class);
  }

  @Override
  @Nullable
  public PsiElement getOpDot() {
    return findChildByType(OP_DOT);
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

    @NotNull
    public PsiReference getReference() {
        return DPsiImplUtil.getReference(this);
    }
}
