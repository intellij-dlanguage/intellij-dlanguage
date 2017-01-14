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

public class DLanguageShiftExpressionImpl extends ASTWrapperPsiElement implements DLanguageShiftExpression {

  public DLanguageShiftExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitShiftExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageAddExpression getAddExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageAddExpression.class));
  }

  @Override
  @Nullable
  public DLanguageShiftExpression getShiftExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageShiftExpression.class);
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
