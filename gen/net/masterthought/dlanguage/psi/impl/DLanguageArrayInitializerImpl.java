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

public class DLanguageArrayInitializerImpl extends ASTWrapperPsiElement implements DLanguageArrayInitializer {

  public DLanguageArrayInitializerImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitArrayInitializer(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageArrayMemberInitializations getArrayMemberInitializations() {
    return PsiTreeUtil.getChildOfType(this, DLanguageArrayMemberInitializations.class);
  }

  @Override
  @Nullable
  public DLanguageAssignExpression getAssignExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
  }

  @Override
  @NotNull
  public PsiElement getOpBracketLeft() {
    return notNullChild(findChildByType(OP_BRACKET_LEFT));
  }

  @Override
  @NotNull
  public PsiElement getOpBracketRight() {
    return notNullChild(findChildByType(OP_BRACKET_RIGHT));
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

}
