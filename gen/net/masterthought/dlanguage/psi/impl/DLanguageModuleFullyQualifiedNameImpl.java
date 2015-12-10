// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageModuleFullyQualifiedNameImpl extends ASTWrapperPsiElement implements DLanguageModuleFullyQualifiedName {

  public DLanguageModuleFullyQualifiedNameImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitModuleFullyQualifiedName(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageModuleFullyQualifiedName getModuleFullyQualifiedName() {
    return findChildByClass(DLanguageModuleFullyQualifiedName.class);
  }

  @Override
  @Nullable
  public PsiElement getOpDot() {
    return findChildByType(OP_DOT);
  }

}
