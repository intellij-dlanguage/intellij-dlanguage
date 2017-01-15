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

public class DLanguageAsmBrExpImpl extends ASTWrapperPsiElement implements DLanguageAsmBrExp {

  public DLanguageAsmBrExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAsmBrExp(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAsmExp getAsmExp() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAsmExp.class);
  }

  @Override
  @NotNull
  public DLanguageAsmUnaExp getAsmUnaExp() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageAsmUnaExp.class));
  }

  @Override
  @Nullable
  public PsiElement getOpBracketLeft() {
    return findChildByType(OP_BRACKET_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpBracketRight() {
    return findChildByType(OP_BRACKET_RIGHT);
  }

}
