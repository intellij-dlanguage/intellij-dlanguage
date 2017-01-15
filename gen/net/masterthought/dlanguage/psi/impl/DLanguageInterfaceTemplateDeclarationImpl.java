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

public class DLanguageInterfaceTemplateDeclarationImpl extends ASTWrapperPsiElement implements DLanguageInterfaceTemplateDeclaration {

  public DLanguageInterfaceTemplateDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitInterfaceTemplateDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageAggregateBody getAggregateBody() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageAggregateBody.class));
  }

  @Override
  @Nullable
  public DLanguageBaseInterfaceList getBaseInterfaceList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBaseInterfaceList.class);
  }

  @Override
  @Nullable
  public DLanguageConstraint getConstraint() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConstraint.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class));
  }

  @Override
  @NotNull
  public DLanguageTemplateParameters getTemplateParameters() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameters.class));
  }

  @Override
  @NotNull
  public PsiElement getKwInterface() {
    return notNullChild(findChildByType(KW_INTERFACE));
  }

}
