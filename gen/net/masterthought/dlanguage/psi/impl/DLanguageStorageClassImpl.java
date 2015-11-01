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

public class DLanguageStorageClassImpl extends ASTWrapperPsiElement implements DLanguageStorageClass {

  public DLanguageStorageClassImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitStorageClass(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAlignAttribute getAlignAttribute() {
    return findChildByClass(DLanguageAlignAttribute.class);
  }

  @Override
  @Nullable
  public DLanguageLinkageAttribute getLinkageAttribute() {
    return findChildByClass(DLanguageLinkageAttribute.class);
  }

  @Override
  @Nullable
  public DLanguageProperty getProperty() {
    return findChildByClass(DLanguageProperty.class);
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
  public PsiElement getKwDeprecated() {
    return findChildByType(KW_DEPRECATED);
  }

  @Override
  @Nullable
  public PsiElement getKwEnum() {
    return findChildByType(KW_ENUM);
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
