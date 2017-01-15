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

public class DLanguageAssertExpressionImpl extends ASTWrapperPsiElement implements DLanguageAssertExpression {

  public DLanguageAssertExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAssertExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<DLanguageAssignExpression> getAssignExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAssignExpression.class);
  }

  @Override
  @NotNull
  public PsiElement getKwAssert() {
    return notNullChild(findChildByType(KW_ASSERT));
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

  @Override
  @NotNull
  public PsiElement getOpParLeft() {
    return notNullChild(findChildByType(OP_PAR_LEFT));
  }

  @Override
  @NotNull
  public PsiElement getOpParRight() {
    return notNullChild(findChildByType(OP_PAR_RIGHT));
  }

}
