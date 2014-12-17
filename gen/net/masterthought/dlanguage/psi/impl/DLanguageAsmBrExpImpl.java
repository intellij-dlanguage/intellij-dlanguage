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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAsmBrExp(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAsmBrExp getAsmBrExp() {
    return findChildByClass(DLanguageAsmBrExp.class);
  }

  @Override
  @Nullable
  public DLanguageAsmExp getAsmExp() {
    return findChildByClass(DLanguageAsmExp.class);
  }

  @Override
  @Nullable
  public DLanguageAsmUnaExp getAsmUnaExp() {
    return findChildByClass(DLanguageAsmUnaExp.class);
  }

  @Override
  @Nullable
  public PsiElement getLBracket() {
    return findChildByType(LBRACKET);
  }

  @Override
  @Nullable
  public PsiElement getRBracket() {
    return findChildByType(RBRACKET);
  }

}
