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

public class DLanguageIsExpressionImpl extends ASTWrapperPsiElement implements DLanguageIsExpression {

  public DLanguageIsExpressionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitIsExpression(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateParameterList getTemplateParameterList() {
    return findChildByClass(DLanguageTemplateParameterList.class);
  }

  @Override
  @NotNull
  public DLanguageType getType() {
    return findNotNullChildByClass(DLanguageType.class);
  }

  @Override
  @Nullable
  public DLanguageTypeSpecialization getTypeSpecialization() {
    return findChildByClass(DLanguageTypeSpecialization.class);
  }

  @Override
  @NotNull
  public PsiElement getKwIs() {
    return findNotNullChildByType(KW_IS);
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

  @Override
  @Nullable
  public PsiElement getOpEqEq() {
    return findChildByType(OP_EQ_EQ);
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
