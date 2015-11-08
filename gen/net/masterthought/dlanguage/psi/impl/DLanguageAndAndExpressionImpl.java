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

public class DLanguageAndAndExpressionImpl extends ASTWrapperPsiElement implements DLanguageAndAndExpression {

  public DLanguageAndAndExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAndAndExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAndAndExpression getAndAndExpression() {
    return findChildByClass(DLanguageAndAndExpression.class);
  }

  @Override
  @Nullable
  public DLanguageCmpExpression getCmpExpression() {
    return findChildByClass(DLanguageCmpExpression.class);
  }

  @Override
  @Nullable
  public DLanguageOrExpression getOrExpression() {
    return findChildByClass(DLanguageOrExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getOpBoolAnd() {
    return findChildByType(OP_BOOL_AND);
  }

}
