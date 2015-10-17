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

public class DLanguageMixinDeclarationImpl extends ASTWrapperPsiElement implements DLanguageMixinDeclaration {

  public DLanguageMixinDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitMixinDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageMixinExpression getMixinExpression() {
    return findChildByClass(DLanguageMixinExpression.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateMixinExpression getTemplateMixinExpression() {
    return findChildByClass(DLanguageTemplateMixinExpression.class);
  }

  @Override
  @NotNull
  public PsiElement getOpScolon() {
    return findNotNullChildByType(OP_SCOLON);
  }

}
