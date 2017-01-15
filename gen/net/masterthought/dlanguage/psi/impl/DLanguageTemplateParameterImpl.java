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

public class DLanguageTemplateParameterImpl extends ASTWrapperPsiElement implements DLanguageTemplateParameter {

  public DLanguageTemplateParameterImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitTemplateParameter(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageTemplateAliasParameter getTemplateAliasParameter() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateAliasParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateThisParameter getTemplateThisParameter() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateThisParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateTypeParameter getTemplateTypeParameter() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateTypeParameter.class);
  }

}
