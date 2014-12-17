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

public class DLanguageBuiltinTypeImpl extends ASTWrapperPsiElement implements DLanguageBuiltinType {

  public DLanguageBuiltinTypeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitBuiltinType(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getBool() {
    return findChildByType(BOOL);
  }

  @Override
  @Nullable
  public PsiElement getByte() {
    return findChildByType(BYTE);
  }

  @Override
  @Nullable
  public PsiElement getCdouble() {
    return findChildByType(CDOUBLE);
  }

  @Override
  @Nullable
  public PsiElement getCfloat() {
    return findChildByType(CFLOAT);
  }

  @Override
  @Nullable
  public PsiElement getChar() {
    return findChildByType(CHAR);
  }

  @Override
  @Nullable
  public PsiElement getCreal() {
    return findChildByType(CREAL);
  }

  @Override
  @Nullable
  public PsiElement getDchar() {
    return findChildByType(DCHAR);
  }

  @Override
  @Nullable
  public PsiElement getDouble() {
    return findChildByType(DOUBLE);
  }

  @Override
  @Nullable
  public PsiElement getFloat() {
    return findChildByType(FLOAT);
  }

  @Override
  @Nullable
  public PsiElement getIdouble() {
    return findChildByType(IDOUBLE);
  }

  @Override
  @Nullable
  public PsiElement getIfloat() {
    return findChildByType(IFLOAT);
  }

  @Override
  @Nullable
  public PsiElement getInt() {
    return findChildByType(INT);
  }

  @Override
  @Nullable
  public PsiElement getIreal() {
    return findChildByType(IREAL);
  }

  @Override
  @Nullable
  public PsiElement getLong() {
    return findChildByType(LONG);
  }

  @Override
  @Nullable
  public PsiElement getReal() {
    return findChildByType(REAL);
  }

  @Override
  @Nullable
  public PsiElement getShort() {
    return findChildByType(SHORT);
  }

  @Override
  @Nullable
  public PsiElement getUbyte() {
    return findChildByType(UBYTE);
  }

  @Override
  @Nullable
  public PsiElement getUint() {
    return findChildByType(UINT);
  }

  @Override
  @Nullable
  public PsiElement getUlong() {
    return findChildByType(ULONG);
  }

  @Override
  @Nullable
  public PsiElement getUshort() {
    return findChildByType(USHORT);
  }

  @Override
  @Nullable
  public PsiElement getVoid() {
    return findChildByType(VOID);
  }

  @Override
  @Nullable
  public PsiElement getWchar() {
    return findChildByType(WCHAR);
  }

}
