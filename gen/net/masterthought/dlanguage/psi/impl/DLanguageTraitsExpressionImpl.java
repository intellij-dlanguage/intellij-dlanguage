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

public class DLanguageTraitsExpressionImpl extends ASTWrapperPsiElement implements DLanguageTraitsExpression {

  public DLanguageTraitsExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitTraitsExpression(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getComma() {
    return findNotNullChildByType(COMMA);
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
  @NotNull
  public PsiElement getTemplateArgumentList() {
    return findNotNullChildByType(TEMPLATEARGUMENTLIST);
  }

  @Override
  @NotNull
  public PsiElement getTraits() {
    return findNotNullChildByType(TRAITS);
  }

}
