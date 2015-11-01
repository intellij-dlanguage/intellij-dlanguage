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

public class DLanguageTemplateAliasParameterImpl extends ASTWrapperPsiElement implements DLanguageTemplateAliasParameter {

  public DLanguageTemplateAliasParameterImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitTemplateAliasParameter(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageBasicType getBasicType() {
    return findChildByClass(DLanguageBasicType.class);
  }

  @Override
  @Nullable
  public DLanguageDeclarator getDeclarator() {
    return findChildByClass(DLanguageDeclarator.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateAliasParameterDefault getTemplateAliasParameterDefault() {
    return findChildByClass(DLanguageTemplateAliasParameterDefault.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateAliasParameterSpecialization getTemplateAliasParameterSpecialization() {
    return findChildByClass(DLanguageTemplateAliasParameterSpecialization.class);
  }

  @Override
  @NotNull
  public PsiElement getKwAlias() {
    return findNotNullChildByType(KW_ALIAS);
  }

}
