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

public class DLanguageTemplateAliasParameterImpl extends ASTWrapperPsiElement implements DLanguageTemplateAliasParameter {

  public DLanguageTemplateAliasParameterImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitTemplateAliasParameter(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<DLanguageAssignExpression> getAssignExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAssignExpression.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public List<DLanguageType> getTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageType.class);
  }

  @Override
  @NotNull
  public PsiElement getKwAlias() {
    return notNullChild(findChildByType(KW_ALIAS));
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
  }

}
