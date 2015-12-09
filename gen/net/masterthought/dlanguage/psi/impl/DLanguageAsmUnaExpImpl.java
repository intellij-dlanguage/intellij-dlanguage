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

public class DLanguageAsmUnaExpImpl extends ASTWrapperPsiElement implements DLanguageAsmUnaExp {

  public DLanguageAsmUnaExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAsmUnaExp(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAsmExp getAsmExp() {
    return findChildByClass(DLanguageAsmExp.class);
  }

  @Override
  @Nullable
  public DLanguageAsmPrimaryExp getAsmPrimaryExp() {
    return findChildByClass(DLanguageAsmPrimaryExp.class);
  }

  @Override
  @Nullable
  public DLanguageAsmTypePrefix getAsmTypePrefix() {
    return findChildByClass(DLanguageAsmTypePrefix.class);
  }

  @Override
  @Nullable
  public DLanguageAsmUnaExp getAsmUnaExp() {
    return findChildByClass(DLanguageAsmUnaExp.class);
  }

  @Override
  @Nullable
  public PsiElement getOpMinus() {
    return findChildByType(OP_MINUS);
  }

  @Override
  @Nullable
  public PsiElement getOpNot() {
    return findChildByType(OP_NOT);
  }

  @Override
  @Nullable
  public PsiElement getOpPlus() {
    return findChildByType(OP_PLUS);
  }

  @Override
  @Nullable
  public PsiElement getOpTilda() {
    return findChildByType(OP_TILDA);
  }

}
