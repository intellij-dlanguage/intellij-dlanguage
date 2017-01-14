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

public class DLanguageStructDeclarationImpl extends ASTWrapperPsiElement implements DLanguageStructDeclaration {

  public DLanguageStructDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitStructDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAggregateBody getAggregateBody() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAggregateBody.class);
  }

  @Override
  @Nullable
  public DLanguageConstraint getConstraint() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateParameters getTemplateParameters() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class);
  }

  @Override
  @NotNull
  public PsiElement getKwStruct() {
    return notNullChild(findChildByType(KW_STRUCT));
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
