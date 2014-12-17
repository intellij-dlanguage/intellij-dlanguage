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

public class DLanguageLambdaExpressionImpl extends ASTWrapperPsiElement implements DLanguageLambdaExpression {

  public DLanguageLambdaExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitLambdaExpression(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageAssignExpression getAssignExpression() {
    return findNotNullChildByClass(DLanguageAssignExpression.class);
  }

  @Override
  @NotNull
  public List<DLanguageFunctionAttribute> getFunctionAttributeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageFunctionAttribute.class);
  }

  @Override
  @Nullable
  public DLanguageParameters getParameters() {
    return findChildByClass(DLanguageParameters.class);
  }

  @Override
  @Nullable
  public PsiElement getDelegate() {
    return findChildByType(DELEGATE);
  }

  @Override
  @Nullable
  public PsiElement getFunction() {
    return findChildByType(FUNCTION);
  }

  @Override
  @NotNull
  public PsiElement getGoesTo() {
    return findNotNullChildByType(GOESTO);
  }

}
