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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitIfStatement(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageElseStatement getElseStatement() {
    return findChildByClass(DLanguageElseStatement.class);
  }

  @Override
  @NotNull
  public DLanguageIfCondition getIfCondition() {
    return findNotNullChildByClass(DLanguageIfCondition.class);
  }

  @Override
  @NotNull
  public DLanguageThenStatement getThenStatement() {
    return findNotNullChildByClass(DLanguageThenStatement.class);
  }

  @Override
  @Nullable
  public PsiElement getKwElse() {
    return findChildByType(KW_ELSE);
  }

  @Override
  @NotNull
  public PsiElement getKwIf() {
    return findNotNullChildByType(KW_IF);
  }

  @Override
  @NotNull
  public PsiElement getOpParLeft() {
    return findNotNullChildByType(OP_PAR_LEFT);
  }

  @Override
  @NotNull
  public PsiElement getOpParRight() {
    return findNotNullChildByType(OP_PAR_RIGHT);
  }

}
