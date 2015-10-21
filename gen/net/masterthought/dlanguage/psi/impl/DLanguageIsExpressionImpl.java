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
  @NotNull
  public List<DLanguageIdentifier> getIdentifierList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public List<DLanguageTemplateParameterList> getTemplateParameterListList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTemplateParameterList.class);
  }

  @Override
  @NotNull
  public List<DLanguageType> getTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageType.class);
  }

  @Override
  @NotNull
  public List<DLanguageTypeSpecialization> getTypeSpecializationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTypeSpecialization.class);
  }

}
