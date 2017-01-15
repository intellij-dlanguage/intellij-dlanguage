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

public class DLanguagePowExpressionImpl extends ASTWrapperPsiElement implements DLanguagePowExpression {

  public DLanguagePowExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitPowExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguagePostfixExpression getPostfixExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguagePostfixExpression.class));
  }

  @Override
  @Nullable
  public DLanguagePowExpression getPowExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguagePowExpression.class);
  }

  @Override
  @Nullable
  public DLanguageUnaryExpression getUnaryExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getOpPow() {
    return findChildByType(OP_POW);
  }

}
