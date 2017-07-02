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

public class DLanguageIdentityExpressionImpl extends ASTWrapperPsiElement implements DLanguageIdentityExpression {

  public DLanguageIdentityExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitIdentityExpression(this);
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
  public DLanguageAssignExpression getAssignExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
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
  @Nullable
  public DLanguageUnaryExpression getUnaryExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getKwIs() {
    return findChildByType(KW_IS);
  }

  @Override
  @Nullable
  public PsiElement getKwNotIs() {
    return findChildByType(KW_NOT_IS);
  }

}
