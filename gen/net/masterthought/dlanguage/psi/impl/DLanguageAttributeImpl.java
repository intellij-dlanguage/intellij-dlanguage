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

public class DLanguageAttributeImpl extends ASTWrapperPsiElement implements DLanguageAttribute {

  public DLanguageAttributeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAttribute(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAlignAttribute getAlignAttribute() {
    return findChildByClass(DLanguageAlignAttribute.class);
  }

  @Override
  @Nullable
  public DLanguageAtAttribute getAtAttribute() {
    return findChildByClass(DLanguageAtAttribute.class);
  }

  @Override
  @Nullable
  public DLanguageDeprecated getDeprecated() {
    return findChildByClass(DLanguageDeprecated.class);
  }

  @Override
  @Nullable
  public DLanguageLinkageAttribute getLinkageAttribute() {
    return findChildByClass(DLanguageLinkageAttribute.class);
  }

  @Override
  @Nullable
  public DLanguagePragmaExpression getPragmaExpression() {
    return findChildByClass(DLanguagePragmaExpression.class);
  }

  @Override
  @Nullable
  public PsiElement getKwAbstract() {
    return findChildByType(KW_ABSTRACT);
  }

  @Override
  @Nullable
  public PsiElement getKwAuto() {
    return findChildByType(KW_AUTO);
  }

  @Override
  @Nullable
  public PsiElement getKwConst() {
    return findChildByType(KW_CONST);
  }

  @Override
  @Nullable
  public PsiElement getKwExport() {
    return findChildByType(KW_EXPORT);
  }

  @Override
  @Nullable
  public PsiElement getKwExtern() {
    return findChildByType(KW_EXTERN);
  }

  @Override
  @Nullable
  public PsiElement getKwFinal() {
    return findChildByType(KW_FINAL);
  }

  @Override
  @Nullable
  public PsiElement getKwImmutable() {
    return findChildByType(KW_IMMUTABLE);
  }

  @Override
  @Nullable
  public PsiElement getKwInout() {
    return findChildByType(KW_INOUT);
  }

  @Override
  @Nullable
  public PsiElement getKwNothrow() {
    return findChildByType(KW_NOTHROW);
  }

  @Override
  @Nullable
  public PsiElement getKwOverride() {
    return findChildByType(KW_OVERRIDE);
  }

  @Override
  @Nullable
  public PsiElement getKwPackage() {
    return findChildByType(KW_PACKAGE);
  }

  @Override
  @Nullable
  public PsiElement getKwPrivate() {
    return findChildByType(KW_PRIVATE);
  }

  @Override
  @Nullable
  public PsiElement getKwProtected() {
    return findChildByType(KW_PROTECTED);
  }

  @Override
  @Nullable
  public PsiElement getKwPublic() {
    return findChildByType(KW_PUBLIC);
  }

  @Override
  @Nullable
  public PsiElement getKwPure() {
    return findChildByType(KW_PURE);
  }

  @Override
  @Nullable
  public PsiElement getKwRef() {
    return findChildByType(KW_REF);
  }

  @Override
  @Nullable
  public PsiElement getKwScope() {
    return findChildByType(KW_SCOPE);
  }

  @Override
  @Nullable
  public PsiElement getKwShared() {
    return findChildByType(KW_SHARED);
  }

  @Override
  @Nullable
  public PsiElement getKwStatic() {
    return findChildByType(KW_STATIC);
  }

  @Override
  @Nullable
  public PsiElement getKwSynchronized() {
    return findChildByType(KW_SYNCHRONIZED);
  }

}
