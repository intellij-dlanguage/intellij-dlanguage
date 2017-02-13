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

public class DLanguageIfStatementImpl extends ASTWrapperPsiElement implements DLanguageIfStatement {

  public DLanguageIfStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitIfStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageElseStatement getElseStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageElseStatement.class);
  }

  @Override
  @NotNull
  public DLanguageIfCondition getIfCondition() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageIfCondition.class));
  }

  @Override
  @NotNull
  public DLanguageThenStatement getThenStatement() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageThenStatement.class));
  }

  @Override
  @Nullable
  public PsiElement getKwElse() {
    return findChildByType(KW_ELSE);
  }

  @Override
  @NotNull
  public PsiElement getKwIf() {
    return notNullChild(findChildByType(KW_IF));
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

}
