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

public class DLanguageXorExpression_Impl extends ASTWrapperPsiElement implements DLanguageXorExpression_ {

  public DLanguageXorExpression_Impl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitXorExpression_(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAddExpression_ getAddExpression_() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAddExpression_.class);
  }

  @Override
  @Nullable
  public DLanguageAndExxpression_ getAndExxpression_() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAndExxpression_.class);
  }

  @Override
  @Nullable
  public DLanguageMulExpression_ getMulExpression_() {
    return PsiTreeUtil.getChildOfType(this, DLanguageMulExpression_.class);
  }

  @Override
  @Nullable
  public DLanguageShiftExpression_ getShiftExpression_() {
    return PsiTreeUtil.getChildOfType(this, DLanguageShiftExpression_.class);
  }

  @Override
  @NotNull
  public DLanguageUnaryExpression getUnaryExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class));
  }

  @Override
  @Nullable
  public DLanguageXorExpression_ getXorExpression_() {
    return PsiTreeUtil.getChildOfType(this, DLanguageXorExpression_.class);
  }

  @Override
  @NotNull
  public PsiElement getOpXor() {
    return notNullChild(findChildByType(OP_XOR));
  }

}
