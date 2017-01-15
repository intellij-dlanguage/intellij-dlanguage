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

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAttribute(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAlignAttribute getAlignAttribute() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAlignAttribute.class);
  }

  @Override
  @Nullable
  public DLanguageDeprecatedAttribute getDeprecatedAttribute() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeprecatedAttribute.class);
  }

  @Override
  @Nullable
  public DLanguageLinkageAttribute getLinkageAttribute() {
    return PsiTreeUtil.getChildOfType(this, DLanguageLinkageAttribute.class);
  }

  @Override
  @Nullable
  public DLanguagePragma getPragma() {
    return PsiTreeUtil.getChildOfType(this, DLanguagePragma.class);
  }

  @Override
  @Nullable
  public DLanguageProperty getProperty() {
    return PsiTreeUtil.getChildOfType(this, DLanguageProperty.class);
  }

  @Override
  @Nullable
  public DLanguageProtectionAttribute getProtectionAttribute() {
    return PsiTreeUtil.getChildOfType(this, DLanguageProtectionAttribute.class);
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

  @Override
  @Nullable
  public PsiElement getKwGshared() {
    return findChildByType(KW___GSHARED);
  }

}
