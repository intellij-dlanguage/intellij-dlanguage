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

public class DLanguageVersionConditionImpl extends ASTWrapperPsiElement implements DLanguageVersionCondition {

  public DLanguageVersionConditionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitVersionCondition(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageIntegerLiteral getIntegerLiteral() {
    return findChildByClass(DLanguageIntegerLiteral.class);
  }

  @Override
  @Nullable
  public PsiElement getAssert() {
    return findChildByType(ASSERT);
  }

  @Override
  @NotNull
  public PsiElement getLParen() {
    return findNotNullChildByType(LPAREN);
  }

  @Override
  @NotNull
  public PsiElement getRParen() {
    return findNotNullChildByType(RPAREN);
  }

  @Override
  @Nullable
  public PsiElement getTUnittest() {
    return findChildByType(TUNITTEST);
  }

  @Override
  @NotNull
  public PsiElement getVersion() {
    return findNotNullChildByType(VERSION);
  }

}
