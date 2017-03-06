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

public class DLanguageTemplateMixinImpl extends ASTWrapperPsiElement implements DLanguageTemplateMixin {

  public DLanguageTemplateMixinImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitTemplateMixin(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public DLanguageMixinTemplateName getMixinTemplateName() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageMixinTemplateName.class));
  }

  @Override
  @Nullable
  public DLanguageTemplateArguments getTemplateArguments() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateArguments.class);
  }

  @Override
  @NotNull
  public PsiElement getKwMixin() {
    return notNullChild(findChildByType(KW_MIXIN));
  }

  @Override
  @NotNull
  public PsiElement getOpScolon() {
    return notNullChild(findChildByType(OP_SCOLON));
  }

  public DLanguageTemplateDeclaration getTemplateDeclaration() {
    return DPsiImplUtil.getTemplateDeclaration(this);
  }

  public DLanguageTemplateMixinDeclaration getTemplateMixinDeclaration() {
    return DPsiImplUtil.getTemplateMixinDeclaration(this);
  }

}
