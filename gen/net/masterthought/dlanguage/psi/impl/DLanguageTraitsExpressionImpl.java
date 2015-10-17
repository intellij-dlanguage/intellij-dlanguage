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
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public DLanguageTemplateArgumentList getTemplateArgumentList() {
    return findNotNullChildByClass(DLanguageTemplateArgumentList.class);
  }

  @Override
  @NotNull
  public PsiElement getKwTraits() {
    return findNotNullChildByType(KW___TRAITS);
  }

  @Override
  @NotNull
  public PsiElement getOpComma() {
    return findNotNullChildByType(OP_COMMA);
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
