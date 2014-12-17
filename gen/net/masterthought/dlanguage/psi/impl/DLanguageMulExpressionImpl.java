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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitMulExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageMulExpression getMulExpression() {
    return findChildByClass(DLanguageMulExpression.class);
  }

  @Override
  @NotNull
  public DLanguagePowExpression getPowExpression() {
    return findNotNullChildByClass(DLanguagePowExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getDiv() {
    return findChildByType(DIV);
  }

  @Override
  @Nullable
  public PsiElement getMod() {
    return findChildByType(MOD);
  }

  @Override
  @Nullable
  public PsiElement getStar() {
    return findChildByType(STAR);
  }

}
