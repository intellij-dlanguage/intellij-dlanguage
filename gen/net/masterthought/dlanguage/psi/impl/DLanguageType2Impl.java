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

public class DLanguageType2Impl extends ASTWrapperPsiElement implements DLanguageType2 {

  public DLanguageType2Impl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitType2(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageBuiltinType getBuiltinType() {
    return findChildByClass(DLanguageBuiltinType.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifierOrTemplateChain getIdentifierOrTemplateChain() {
    return findChildByClass(DLanguageIdentifierOrTemplateChain.class);
  }

  @Override
  @Nullable
  public DLanguageSymbol getSymbol() {
    return findChildByClass(DLanguageSymbol.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

  @Override
  @Nullable
  public DLanguageTypeConstructor getTypeConstructor() {
    return findChildByClass(DLanguageTypeConstructor.class);
  }

  @Override
  @Nullable
  public DLanguageTypeofExpression getTypeofExpression() {
    return findChildByClass(DLanguageTypeofExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getDot() {
    return findChildByType(DOT);
  }

  @Override
  @Nullable
  public PsiElement getLParen() {
    return findChildByType(LPAREN);
  }

  @Override
  @Nullable
  public PsiElement getRParen() {
    return findChildByType(RPAREN);
  }

}
