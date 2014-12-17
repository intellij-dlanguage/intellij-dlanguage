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
  @Nullable
  public DLanguageExpression getExpression() {
    return findChildByClass(DLanguageExpression.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateValueParameterDefault getTemplateValueParameterDefault() {
    return findChildByClass(DLanguageTemplateValueParameterDefault.class);
  }

  @Override
  @NotNull
  public DLanguageType getType() {
    return findNotNullChildByClass(DLanguageType.class);
  }

}
