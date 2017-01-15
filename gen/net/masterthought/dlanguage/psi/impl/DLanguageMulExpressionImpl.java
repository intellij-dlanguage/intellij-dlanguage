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

public class DLanguageMulExpressionImpl extends ASTWrapperPsiElement implements DLanguageMulExpression {

  public DLanguageMulExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitMulExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageMulExpression getMulExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageMulExpression.class);
  }

  @Override
  @NotNull
  public DLanguageUnaryExpression getUnaryExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class));
  }

  @Override
  @Nullable
  public PsiElement getOpAsterisk() {
    return findChildByType(OP_ASTERISK);
  }

  @Override
  @Nullable
  public PsiElement getOpDiv() {
    return findChildByType(OP_DIV);
  }

  @Override
  @Nullable
  public PsiElement getOpMod() {
    return findChildByType(OP_MOD);
  }

}
