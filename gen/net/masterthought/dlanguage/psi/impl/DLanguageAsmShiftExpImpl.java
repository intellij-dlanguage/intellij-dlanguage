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

public class DLanguageAsmShiftExpImpl extends ASTWrapperPsiElement implements DLanguageAsmShiftExp {

  public DLanguageAsmShiftExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAsmShiftExp(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageAsmAddExp getAsmAddExp() {
    return findNotNullChildByClass(DLanguageAsmAddExp.class);
  }

  @Override
  @Nullable
  public DLanguageAsmShiftExp getAsmShiftExp() {
    return findChildByClass(DLanguageAsmShiftExp.class);
  }

  @Override
  @Nullable
  public PsiElement getOpShLeft() {
    return findChildByType(OP_SH_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpShRight() {
    return findChildByType(OP_SH_RIGHT);
  }

  @Override
  @Nullable
  public PsiElement getOpUshRight() {
    return findChildByType(OP_USH_RIGHT);
  }

}
