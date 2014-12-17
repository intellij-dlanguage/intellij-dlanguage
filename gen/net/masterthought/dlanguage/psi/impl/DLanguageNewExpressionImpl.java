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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitNewExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageArguments getArguments() {
    return findChildByClass(DLanguageArguments.class);
  }

  @Override
  @Nullable
  public DLanguageAssignExpression getAssignExpression() {
    return findChildByClass(DLanguageAssignExpression.class);
  }

  @Override
  @Nullable
  public DLanguageNewAnonClassExpression getNewAnonClassExpression() {
    return findChildByClass(DLanguageNewAnonClassExpression.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

  @Override
  @Nullable
  public PsiElement getLBracket() {
    return findChildByType(LBRACKET);
  }

  @Override
  @Nullable
  public PsiElement getNew() {
    return findChildByType(NEW);
  }

  @Override
  @Nullable
  public PsiElement getRBracket() {
    return findChildByType(RBRACKET);
  }

}
