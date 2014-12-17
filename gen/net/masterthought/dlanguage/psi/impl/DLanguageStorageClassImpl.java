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
  public DLanguageTypeConstructor getTypeConstructor() {
    return findChildByClass(DLanguageTypeConstructor.class);
  }

  @Override
  @Nullable
  public PsiElement getAbstract() {
    return findChildByType(ABSTRACT);
  }

  @Override
  @Nullable
  public PsiElement getAuto() {
    return findChildByType(AUTO);
  }

  @Override
  @Nullable
  public PsiElement getEnum() {
    return findChildByType(ENUM);
  }

  @Override
  @Nullable
  public PsiElement getExtern() {
    return findChildByType(EXTERN);
  }

  @Override
  @Nullable
  public PsiElement getFinal() {
    return findChildByType(FINAL);
  }

  @Override
  @Nullable
  public PsiElement getGshared() {
    return findChildByType(GSHARED);
  }

  @Override
  @Nullable
  public PsiElement getNothrow() {
    return findChildByType(NOTHROW);
  }

  @Override
  @Nullable
  public PsiElement getOverride() {
    return findChildByType(OVERRIDE);
  }

  @Override
  @Nullable
  public PsiElement getPure() {
    return findChildByType(PURE);
  }

  @Override
  @Nullable
  public PsiElement getRef() {
    return findChildByType(REF);
  }

  @Override
  @Nullable
  public PsiElement getScope() {
    return findChildByType(SCOPE);
  }

  @Override
  @Nullable
  public PsiElement getStatic() {
    return findChildByType(STATIC);
  }

  @Override
  @Nullable
  public PsiElement getSynchronized() {
    return findChildByType(SYNCHRONIZED);
  }

}
