// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageDebugSpecificationImpl extends ASTWrapperPsiElement implements DLanguageDebugSpecification {

  public DLanguageDebugSpecificationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitDebugSpecification(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public PsiElement getIntegerLiteral() {
    return findChildByType(INTEGER_LITERAL);
  }

  @Override
  @NotNull
  public PsiElement getKwDebug() {
    return findNotNullChildByType(KW_DEBUG);
  }

  @Override
  @NotNull
  public PsiElement getOpEq() {
    return findNotNullChildByType(OP_EQ);
  }

  @Override
  @NotNull
  public PsiElement getOpScolon() {
    return findNotNullChildByType(OP_SCOLON);
  }

}
