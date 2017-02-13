// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguagePropertyImpl extends ASTWrapperPsiElement implements DLanguageProperty {

  public DLanguagePropertyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitProperty(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguagePropertyIdentifier getPropertyIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguagePropertyIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageUserDefinedAttribute getUserDefinedAttribute() {
    return PsiTreeUtil.getChildOfType(this, DLanguageUserDefinedAttribute.class);
  }

  @Override
  @Nullable
  public PsiElement getOpAt() {
    return findChildByType(OP_AT);
  }

}
