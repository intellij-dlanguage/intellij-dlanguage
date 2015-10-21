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

public class DLanguageAssignExpressionImpl extends ASTWrapperPsiElement implements DLanguageAssignExpression {

  public DLanguageAssignExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAssignExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAssignOperator getAssignOperator() {
    return findChildByClass(DLanguageAssignOperator.class);
  }

  @Override
  @Nullable
  public DLanguageExpression getExpression() {
    return findChildByClass(DLanguageExpression.class);
  }

  @Override
  @NotNull
  public DLanguageTernaryExpression getTernaryExpression() {
    return findNotNullChildByClass(DLanguageTernaryExpression.class);
  }

}
