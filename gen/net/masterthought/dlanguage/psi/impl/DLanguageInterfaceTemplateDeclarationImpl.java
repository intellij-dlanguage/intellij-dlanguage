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
  public List<DLanguageAggregateBody> getAggregateBodyList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAggregateBody.class);
  }

  @Override
  @NotNull
  public List<DLanguageBaseInterfaceList> getBaseInterfaceListList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageBaseInterfaceList.class);
  }

  @Override
  @NotNull
  public List<DLanguageConstraint> getConstraintList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageConstraint.class);
  }

  @Override
  @NotNull
  public List<DLanguageIdentifier> getIdentifierList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public List<DLanguageTemplateParameters> getTemplateParametersList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTemplateParameters.class);
  }

}
