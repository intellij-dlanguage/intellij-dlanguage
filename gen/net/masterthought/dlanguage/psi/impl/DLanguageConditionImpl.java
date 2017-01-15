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

public class DLanguageConditionImpl extends ASTWrapperPsiElement implements DLanguageCondition {

  public DLanguageConditionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitCondition(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageDebugCondition getDebugCondition() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDebugCondition.class);
  }

  @Override
  @Nullable
  public DLanguageStaticIfCondition getStaticIfCondition() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStaticIfCondition.class);
  }

  @Override
  @Nullable
  public DLanguageVersionCondition getVersionCondition() {
    return PsiTreeUtil.getChildOfType(this, DLanguageVersionCondition.class);
  }

}
