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

public class DLanguageAsmLogAndExpImpl extends ASTWrapperPsiElement implements DLanguageAsmLogAndExp {

  public DLanguageAsmLogAndExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAsmLogAndExp(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAsmLogAndExp getAsmLogAndExp() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAsmLogAndExp.class);
  }

  @Override
  @NotNull
  public DLanguageAsmOrExp getAsmOrExp() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageAsmOrExp.class));
  }

  @Override
  @Nullable
  public PsiElement getOpBoolAnd() {
    return findChildByType(OP_BOOL_AND);
  }

}
