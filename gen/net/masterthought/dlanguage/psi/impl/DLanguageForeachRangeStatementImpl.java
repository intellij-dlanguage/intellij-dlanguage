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

public class DLanguageForeachRangeStatementImpl extends ASTWrapperPsiElement implements DLanguageForeachRangeStatement {

  public DLanguageForeachRangeStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitForeachRangeStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageForeach getForeach() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageForeach.class));
  }

  @Override
  @NotNull
  public DLanguageForeachType getForeachType() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageForeachType.class));
  }

  @Override
  @NotNull
  public DLanguageLwrExpression getLwrExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageLwrExpression.class));
  }

  @Override
  @NotNull
  public DLanguageScopeStatement getScopeStatement() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageScopeStatement.class));
  }

  @Override
  @NotNull
  public DLanguageUprExpression getUprExpression() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageUprExpression.class));
  }

  @Override
  @NotNull
  public PsiElement getOpDdot() {
    return notNullChild(findChildByType(OP_DDOT));
  }

  @Override
  @NotNull
  public PsiElement getOpParLeft() {
    return notNullChild(findChildByType(OP_PAR_LEFT));
  }

  @Override
  @NotNull
  public PsiElement getOpParRight() {
    return notNullChild(findChildByType(OP_PAR_RIGHT));
  }

  @Override
  @NotNull
  public PsiElement getOpScolon() {
    return notNullChild(findChildByType(OP_SCOLON));
  }

}
