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

public class DLanguageAddExpressionImpl extends ASTWrapperPsiElement implements DLanguageAddExpression {

  public DLanguageAddExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAddExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAddExpression getAddExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAddExpression.class);
  }

  @Override
  @NotNull
  public DLanguageMulExpression getMulExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageMulExpression.class));
  }

  @Override
  @Nullable
  public PsiElement getOpMinus() {
    return findChildByType(OP_MINUS);
  }

  @Override
  @Nullable
  public PsiElement getOpPlus() {
    return findChildByType(OP_PLUS);
  }

  @Override
  @Nullable
  public PsiElement getOpTilda() {
    return findChildByType(OP_TILDA);
  }

}
