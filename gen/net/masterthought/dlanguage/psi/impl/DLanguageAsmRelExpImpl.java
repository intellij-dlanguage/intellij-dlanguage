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

public class DLanguageAsmRelExpImpl extends ASTWrapperPsiElement implements DLanguageAsmRelExp {

  public DLanguageAsmRelExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAsmRelExp(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAsmRelExp getAsmRelExp() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAsmRelExp.class);
  }

  @Override
  @NotNull
  public DLanguageAsmShiftExp getAsmShiftExp() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageAsmShiftExp.class));
  }

  @Override
  @Nullable
  public PsiElement getOpGt() {
    return findChildByType(OP_GT);
  }

  @Override
  @Nullable
  public PsiElement getOpGtEq() {
    return findChildByType(OP_GT_EQ);
  }

  @Override
  @Nullable
  public PsiElement getOpLess() {
    return findChildByType(OP_LESS);
  }

  @Override
  @Nullable
  public PsiElement getOpLessEq() {
    return findChildByType(OP_LESS_EQ);
  }

}
