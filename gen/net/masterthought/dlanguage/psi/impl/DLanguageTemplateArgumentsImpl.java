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

public class DLanguageTemplateArgumentsImpl extends ASTWrapperPsiElement implements DLanguageTemplateArguments {

  public DLanguageTemplateArgumentsImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitTemplateArguments(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageTemplateArgumentList getTemplateArgumentList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateArgumentList.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateSingleArgument getTemplateSingleArgument() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateSingleArgument.class);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

  @Override
  @NotNull
  public PsiElement getOpNot() {
    return notNullChild(findChildByType(OP_NOT));
  }

  @Override
  @Nullable
  public PsiElement getOpParLeft() {
    return findChildByType(OP_PAR_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpParRight() {
    return findChildByType(OP_PAR_RIGHT);
  }

}
