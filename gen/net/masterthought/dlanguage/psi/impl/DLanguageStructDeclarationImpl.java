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

public class DLanguageStructDeclarationImpl extends ASTWrapperPsiElement implements DLanguageStructDeclaration {

  public DLanguageStructDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitStructDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAggregateBody getAggregateBody() {
    return findChildByClass(DLanguageAggregateBody.class);
  }

  @Override
  @Nullable
  public DLanguageAnonStructDeclaration getAnonStructDeclaration() {
    return findChildByClass(DLanguageAnonStructDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageStructTemplateDeclaration getStructTemplateDeclaration() {
    return findChildByClass(DLanguageStructTemplateDeclaration.class);
  }

  @Override
  @Nullable
  public PsiElement getKwStruct() {
    return findChildByType(KW_STRUCT);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
