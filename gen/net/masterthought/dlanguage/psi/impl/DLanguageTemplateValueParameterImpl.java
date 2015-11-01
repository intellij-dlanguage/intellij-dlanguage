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

public class DLanguageTemplateValueParameterImpl extends ASTWrapperPsiElement implements DLanguageTemplateValueParameter {

  public DLanguageTemplateValueParameterImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitTemplateValueParameter(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageBasicType getBasicType() {
    return findNotNullChildByClass(DLanguageBasicType.class);
  }

  @Override
  @NotNull
  public DLanguageDeclarator getDeclarator() {
    return findNotNullChildByClass(DLanguageDeclarator.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateValueParameterDefault getTemplateValueParameterDefault() {
    return findChildByClass(DLanguageTemplateValueParameterDefault.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateValueParameterSpecialization getTemplateValueParameterSpecialization() {
    return findChildByClass(DLanguageTemplateValueParameterSpecialization.class);
  }

}
