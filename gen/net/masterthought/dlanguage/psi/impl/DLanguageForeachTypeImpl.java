// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageForeachTypeImpl extends ASTWrapperPsiElement implements DLanguageForeachType {

  public DLanguageForeachTypeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitForeachType(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageForeachTypeAttributes getForeachTypeAttributes() {
    return findChildByClass(DLanguageForeachTypeAttributes.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

}
