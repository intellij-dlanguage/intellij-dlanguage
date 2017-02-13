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

public class DLanguageNewExpressionImpl extends ASTWrapperPsiElement implements DLanguageNewExpression {

  public DLanguageNewExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitNewExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAllocatorArguments getAllocatorArguments() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAllocatorArguments.class);
  }

  @Override
  @Nullable
  public DLanguageNewExpressionWithArgs getNewExpressionWithArgs() {
    return PsiTreeUtil.getChildOfType(this, DLanguageNewExpressionWithArgs.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
  }

  @Override
  @Nullable
  public PsiElement getKwNew() {
    return findChildByType(KW_NEW);
  }

}
