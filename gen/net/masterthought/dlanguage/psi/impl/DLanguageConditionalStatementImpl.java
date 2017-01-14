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

public class DLanguageConditionalStatementImpl extends ASTWrapperPsiElement implements DLanguageConditionalStatement {

  public DLanguageConditionalStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitConditionalStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageCondition getCondition() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageCondition.class));
  }

  @Override
  @NotNull
  public List<DLanguageStatement> getStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageStatement.class);
  }

  @Override
  @Nullable
  public PsiElement getKwElse() {
    return findChildByType(KW_ELSE);
  }

}
