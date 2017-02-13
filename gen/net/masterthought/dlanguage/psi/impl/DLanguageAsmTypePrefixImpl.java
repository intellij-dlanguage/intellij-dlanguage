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

public class DLanguageAsmTypePrefixImpl extends ASTWrapperPsiElement implements DLanguageAsmTypePrefix {

  public DLanguageAsmTypePrefixImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAsmTypePrefix(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getKwByte() {
    return findChildByType(KW_BYTE);
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
  public PsiElement getKwInt() {
    return findChildByType(KW_INT);
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

}
