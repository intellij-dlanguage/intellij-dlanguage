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

public class DLanguageFunctionBodyImpl extends ASTWrapperPsiElement implements DLanguageFunctionBody {

  public DLanguageFunctionBodyImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitFunctionBody(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageBlockStatement getBlockStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBlockStatement.class);
  }

  @Override
  @Nullable
  public DLanguageBodyStatement getBodyStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBodyStatement.class);
  }

  @Override
  @Nullable
  public DLanguageFunctionContracts getFunctionContracts() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFunctionContracts.class);
  }

}
