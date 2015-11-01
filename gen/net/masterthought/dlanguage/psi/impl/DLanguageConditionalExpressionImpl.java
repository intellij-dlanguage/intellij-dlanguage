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

public class DLanguageConditionalExpressionImpl extends ASTWrapperPsiElement implements DLanguageConditionalExpression {

  public DLanguageConditionalExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitConditionalExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageConditionalExpression getConditionalExpression() {
    return findChildByClass(DLanguageConditionalExpression.class);
  }

  @Override
  @Nullable
  public DLanguageExpression getExpression() {
    return findChildByClass(DLanguageExpression.class);
  }

  @Override
  @NotNull
  public DLanguageOrOrExpression getOrOrExpression() {
    return findNotNullChildByClass(DLanguageOrOrExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

  @Override
  @Nullable
  public PsiElement getOpQuest() {
    return findChildByType(OP_QUEST);
  }

}
