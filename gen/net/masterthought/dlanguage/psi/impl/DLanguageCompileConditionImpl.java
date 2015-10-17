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

public class DLanguageCompileConditionImpl extends ASTWrapperPsiElement implements DLanguageCompileCondition {

  public DLanguageCompileConditionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitCompileCondition(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageDebugCondition getDebugCondition() {
    return findChildByClass(DLanguageDebugCondition.class);
  }

  @Override
  @Nullable
  public DLanguageStaticIfCondition getStaticIfCondition() {
    return findChildByClass(DLanguageStaticIfCondition.class);
  }

  @Override
  @Nullable
  public DLanguageVersionCondition getVersionCondition() {
    return findChildByClass(DLanguageVersionCondition.class);
  }

}
