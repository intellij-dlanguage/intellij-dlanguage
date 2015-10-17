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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitTemplateParameter(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageTemplateAliasParameter getTemplateAliasParameter() {
    return findChildByClass(DLanguageTemplateAliasParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateThisParameter getTemplateThisParameter() {
    return findChildByClass(DLanguageTemplateThisParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateTupleParameter getTemplateTupleParameter() {
    return findChildByClass(DLanguageTemplateTupleParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateTypeParameter getTemplateTypeParameter() {
    return findChildByClass(DLanguageTemplateTypeParameter.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateValueParameter getTemplateValueParameter() {
    return findChildByClass(DLanguageTemplateValueParameter.class);
  }

}
