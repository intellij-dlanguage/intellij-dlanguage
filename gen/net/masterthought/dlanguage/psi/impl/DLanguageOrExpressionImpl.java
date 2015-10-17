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

public class DLanguageOrExpressionImpl extends ASTWrapperPsiElement implements DLanguageOrExpression {

  public DLanguageOrExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitOrExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageOrExpression getOrExpression() {
    return findChildByClass(DLanguageOrExpression.class);
  }

  @Override
  @NotNull
  public DLanguageXorExpression getXorExpression() {
    return findNotNullChildByClass(DLanguageXorExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getOpOr() {
    return findChildByType(OP_OR);
  }

}
