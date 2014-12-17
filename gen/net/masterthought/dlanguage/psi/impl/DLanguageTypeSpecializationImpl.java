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

public class DLanguageTypeSpecializationImpl extends ASTWrapperPsiElement implements DLanguageTypeSpecialization {

  public DLanguageTypeSpecializationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitTypeSpecialization(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

//  @Override
//  @Nullable
//  public PsiElement getClass() {
//    return findChildByType(CLASS);
//  }

  @Override
  @Nullable
  public PsiElement getConst() {
    return findChildByType(CONST);
  }

  @Override
  @Nullable
  public PsiElement getDelegate() {
    return findChildByType(DELEGATE);
  }

  @Override
  @Nullable
  public PsiElement getEnum() {
    return findChildByType(ENUM);
  }

  @Override
  @Nullable
  public PsiElement getFunction() {
    return findChildByType(FUNCTION);
  }

  @Override
  @Nullable
  public PsiElement getImmutable() {
    return findChildByType(IMMUTABLE);
  }

  @Override
  @Nullable
  public PsiElement getInout() {
    return findChildByType(INOUT);
  }

  @Override
  @Nullable
  public PsiElement getInterface() {
    return findChildByType(INTERFACE);
  }

  @Override
  @Nullable
  public PsiElement getReturn() {
    return findChildByType(RETURN);
  }

  @Override
  @Nullable
  public PsiElement getShared() {
    return findChildByType(SHARED);
  }

  @Override
  @Nullable
  public PsiElement getStruct() {
    return findChildByType(STRUCT);
  }

  @Override
  @Nullable
  public PsiElement getSuper() {
    return findChildByType(SUPER);
  }

  @Override
  @Nullable
  public PsiElement getTParameters() {
    return findChildByType(TPARAMETERS);
  }

  @Override
  @Nullable
  public PsiElement getTypedef() {
    return findChildByType(TYPEDEF);
  }

  @Override
  @Nullable
  public PsiElement getUnion() {
    return findChildByType(UNION);
  }

}
