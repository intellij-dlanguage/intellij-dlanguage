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

public class DLanguageMixinTemplateNameImpl extends ASTWrapperPsiElement implements DLanguageMixinTemplateName {

  public DLanguageMixinTemplateNameImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitMixinTemplateName(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageQualifiedIdentifierList getQualifiedIdentifierList() {
    return findChildByClass(DLanguageQualifiedIdentifierList.class);
  }

  @Override
  @Nullable
  public DLanguageTypeof getTypeof() {
    return findChildByClass(DLanguageTypeof.class);
  }

  @Override
  @NotNull
  public PsiElement getOpDot() {
    return findNotNullChildByType(OP_DOT);
  }

}
