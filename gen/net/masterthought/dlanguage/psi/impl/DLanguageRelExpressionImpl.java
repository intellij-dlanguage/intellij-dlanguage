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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitRelExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageRelExpression getRelExpression() {
    return findChildByClass(DLanguageRelExpression.class);
  }

  @Override
  @Nullable
  public DLanguageRelOperator getRelOperator() {
    return findChildByClass(DLanguageRelOperator.class);
  }

  @Override
  @NotNull
  public DLanguageShiftExpression getShiftExpression() {
    return findNotNullChildByClass(DLanguageShiftExpression.class);
  }

}
