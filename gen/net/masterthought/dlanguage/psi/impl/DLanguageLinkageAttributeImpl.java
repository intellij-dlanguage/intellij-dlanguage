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

public class DLanguageLinkageAttributeImpl extends ASTWrapperPsiElement implements DLanguageLinkageAttribute {

  public DLanguageLinkageAttributeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitLinkageAttribute(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifierList getIdentifierList() {
    return findChildByClass(DLanguageIdentifierList.class);
  }

  @Override
  @Nullable
  public DLanguageLinkageType getLinkageType() {
    return findChildByClass(DLanguageLinkageType.class);
  }

  @Override
  @NotNull
  public PsiElement getKwExtern() {
    return findNotNullChildByType(KW_EXTERN);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

  @Override
  @NotNull
  public PsiElement getOpParLeft() {
    return findNotNullChildByType(OP_PAR_LEFT);
  }

  @Override
  @NotNull
  public PsiElement getOpParRight() {
    return findNotNullChildByType(OP_PAR_RIGHT);
  }

  @Override
  @Nullable
  public PsiElement getOpPlusPlus() {
    return findChildByType(OP_PLUS_PLUS);
  }

}
