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

public class DLanguageBasicTypeXImpl extends ASTWrapperPsiElement implements DLanguageBasicTypeX {

  public DLanguageBasicTypeXImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitBasicTypeX(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getKwBool() {
    return findChildByType(KW_BOOL);
  }

  @Override
  @Nullable
  public PsiElement getKwByte() {
    return findChildByType(KW_BYTE);
  }

  @Override
  @Nullable
  public PsiElement getKwCdouble() {
    return findChildByType(KW_CDOUBLE);
  }

  @Override
  @Nullable
  public PsiElement getKwCfloat() {
    return findChildByType(KW_CFLOAT);
  }

  @Override
  @Nullable
  public PsiElement getKwChar() {
    return findChildByType(KW_CHAR);
  }

  @Override
  @Nullable
  public PsiElement getKwCreal() {
    return findChildByType(KW_CREAL);
  }

  @Override
  @Nullable
  public PsiElement getKwDchar() {
    return findChildByType(KW_DCHAR);
  }

  @Override
  @Nullable
  public PsiElement getKwDouble() {
    return findChildByType(KW_DOUBLE);
  }

  @Override
  @Nullable
  public PsiElement getKwFloat() {
    return findChildByType(KW_FLOAT);
  }

  @Override
  @Nullable
  public PsiElement getKwIdouble() {
    return findChildByType(KW_IDOUBLE);
  }

  @Override
  @Nullable
  public PsiElement getKwIfloat() {
    return findChildByType(KW_IFLOAT);
  }

  @Override
  @Nullable
  public PsiElement getKwInt() {
    return findChildByType(KW_INT);
  }

  @Override
  @Nullable
  public PsiElement getKwIreal() {
    return findChildByType(KW_IREAL);
  }

  @Override
  @Nullable
  public PsiElement getKwLong() {
    return findChildByType(KW_LONG);
  }

  @Override
  @Nullable
  public PsiElement getKwReal() {
    return findChildByType(KW_REAL);
  }

  @Override
  @Nullable
  public PsiElement getKwShort() {
    return findChildByType(KW_SHORT);
  }

  @Override
  @Nullable
  public PsiElement getKwUbyte() {
    return findChildByType(KW_UBYTE);
  }

  @Override
  @Nullable
  public PsiElement getKwUint() {
    return findChildByType(KW_UINT);
  }

  @Override
  @Nullable
  public PsiElement getKwUlong() {
    return findChildByType(KW_ULONG);
  }

  @Override
  @Nullable
  public PsiElement getKwUshort() {
    return findChildByType(KW_USHORT);
  }

  @Override
  @Nullable
  public PsiElement getKwVoid() {
    return findChildByType(KW_VOID);
  }

  @Override
  @Nullable
  public PsiElement getKwWchar() {
    return findChildByType(KW_WCHAR);
  }

}
