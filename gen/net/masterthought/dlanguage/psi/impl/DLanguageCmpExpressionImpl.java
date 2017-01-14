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

public class DLanguageCmpExpressionImpl extends ASTWrapperPsiElement implements DLanguageCmpExpression {

  public DLanguageCmpExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitCmpExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageEqualExpression getEqualExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageEqualExpression.class);
  }

  @Override
  @Nullable
  public DLanguageIdentityExpression getIdentityExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentityExpression.class);
  }

  @Override
  @Nullable
  public DLanguageInExpression getInExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageInExpression.class);
  }

  @Override
  @Nullable
  public DLanguageRelExpression getRelExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageRelExpression.class);
  }

  @Override
  @Nullable
  public DLanguageShiftExpression getShiftExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageShiftExpression.class);
  }

}
