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

public class DLanguageRelExpressionImpl extends ASTWrapperPsiElement implements DLanguageRelExpression {

  public DLanguageRelExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitRelExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageShiftExpression getShiftExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageShiftExpression.class));
  }

  @Override
  @Nullable
  public PsiElement getOpGt() {
    return findChildByType(OP_GT);
  }

  @Override
  @Nullable
  public PsiElement getOpGtEq() {
    return findChildByType(OP_GT_EQ);
  }

  @Override
  @Nullable
  public PsiElement getOpLess() {
    return findChildByType(OP_LESS);
  }

  @Override
  @Nullable
  public PsiElement getOpLessEq() {
    return findChildByType(OP_LESS_EQ);
  }

  @Override
  @Nullable
  public PsiElement getOpLessGr() {
    return findChildByType(OP_LESS_GR);
  }

  @Override
  @Nullable
  public PsiElement getOpLessGrEq() {
    return findChildByType(OP_LESS_GR_EQ);
  }

  @Override
  @Nullable
  public PsiElement getOpNotGr() {
    return findChildByType(OP_NOT_GR);
  }

  @Override
  @Nullable
  public PsiElement getOpNotGrEq() {
    return findChildByType(OP_NOT_GR_EQ);
  }

  @Override
  @Nullable
  public PsiElement getOpNotLess() {
    return findChildByType(OP_NOT_LESS);
  }

  @Override
  @Nullable
  public PsiElement getOpNotLessEq() {
    return findChildByType(OP_NOT_LESS_EQ);
  }

  @Override
  @Nullable
  public PsiElement getOpUnord() {
    return findChildByType(OP_UNORD);
  }

  @Override
  @Nullable
  public PsiElement getOpUnordEq() {
    return findChildByType(OP_UNORD_EQ);
  }

}
