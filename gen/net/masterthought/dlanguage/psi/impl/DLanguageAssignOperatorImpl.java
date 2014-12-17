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

public class DLanguageAssignOperatorImpl extends ASTWrapperPsiElement implements DLanguageAssignOperator {

  public DLanguageAssignOperatorImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAssignOperator(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getAssign() {
    return findChildByType(ASSIGN);
  }

  @Override
  @Nullable
  public PsiElement getBitAndEqual() {
    return findChildByType(BITANDEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getBitOrEqual() {
    return findChildByType(BITOREQUAL);
  }

  @Override
  @Nullable
  public PsiElement getCatEqual() {
    return findChildByType(CATEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getDivEqual() {
    return findChildByType(DIVEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getMinusEqual() {
    return findChildByType(MINUSEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getModEqual() {
    return findChildByType(MODEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getMulEqual() {
    return findChildByType(MULEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getPlusEqual() {
    return findChildByType(PLUSEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getPowEqual() {
    return findChildByType(POWEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getShiftLeftEqual() {
    return findChildByType(SHIFTLEFTEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getShiftRightEqual() {
    return findChildByType(SHIFTRIGHTEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getUnsignedShiftRightEqual() {
    return findChildByType(UNSIGNEDSHIFTRIGHTEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getXorEqual() {
    return findChildByType(XOREQUAL);
  }

}
