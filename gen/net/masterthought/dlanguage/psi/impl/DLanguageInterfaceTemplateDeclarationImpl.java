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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitInterfaceTemplateDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageAggregateBody getAggregateBody() {
    return findNotNullChildByClass(DLanguageAggregateBody.class);
  }

  @Override
  @Nullable
  public DLanguageBaseInterfaceList getBaseInterfaceList() {
    return findChildByClass(DLanguageBaseInterfaceList.class);
  }

  @Override
  @Nullable
  public DLanguageConstraint getConstraint() {
    return findChildByClass(DLanguageConstraint.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public DLanguageTemplateParameters getTemplateParameters() {
    return findNotNullChildByClass(DLanguageTemplateParameters.class);
  }

  @Override
  @NotNull
  public PsiElement getKwInterface() {
    return findNotNullChildByType(KW_INTERFACE);
  }

}
