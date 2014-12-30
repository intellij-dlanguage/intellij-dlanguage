package net.masterthought.dlanguage.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import net.masterthought.dlanguage.psi.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;


public abstract class DNamedElementImpl extends DCompositeElementImpl implements DCompositeElement, DNamedElement {
  public DNamedElementImpl(ASTNode node) {
    super(node);
  }

  @Override
  public PsiElement setName(@NonNls @NotNull String s) throws IncorrectOperationException {
    return this;
  }
}

