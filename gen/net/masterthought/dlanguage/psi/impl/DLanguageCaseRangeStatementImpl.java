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

public class DLanguageCaseRangeStatementImpl extends ASTWrapperPsiElement implements DLanguageCaseRangeStatement {

  public DLanguageCaseRangeStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitCaseRangeStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageFirstExp getFirstExp() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageFirstExp.class));
  }

  @Override
  @NotNull
  public DLanguageLastExp getLastExp() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageLastExp.class));
  }

  @Override
  @NotNull
  public DLanguageScopeStatementList getScopeStatementList() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageScopeStatementList.class));
  }

  @Override
  @NotNull
  public PsiElement getOpDdot() {
    return notNullChild(findChildByType(OP_DDOT));
  }

}
