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

public class DLanguageParameterImpl extends ASTWrapperPsiElement implements DLanguageParameter {

  public DLanguageParameterImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitParameter(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAssignExpression getAssignExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
  }

  @Override
  @Nullable
  public DLanguageBasicType getBasicType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBasicType.class);
  }

  @Override
  @Nullable
  public DLanguageDeclarator getDeclarator() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclarator.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageInOut getInOut() {
    return PsiTreeUtil.getChildOfType(this, DLanguageInOut.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
  }

  @Override
  @Nullable
  public PsiElement getKwAlias() {
    return findChildByType(KW_ALIAS);
  }

  @Override
  @Nullable
  public PsiElement getOpEq() {
    return findChildByType(OP_EQ);
  }

  @Override
  @Nullable
  public PsiElement getOpTripledot() {
    return findChildByType(OP_TRIPLEDOT);
  }

}
