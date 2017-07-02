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

public class DLanguageShiftExpression_Impl extends ASTWrapperPsiElement implements DLanguageShiftExpression_ {

  public DLanguageShiftExpression_Impl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitShiftExpression_(this);
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
  public PsiElement getOpShLeft() {
    return findChildByType(OP_SH_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpShRight() {
    return findChildByType(OP_SH_RIGHT);
  }

  @Override
  @Nullable
  public PsiElement getOpUshRight() {
    return findChildByType(OP_USH_RIGHT);
  }

}
