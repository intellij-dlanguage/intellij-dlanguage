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

public class DLanguageInOutXImpl extends ASTWrapperPsiElement implements DLanguageInOutX {

  public DLanguageInOutXImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitInOutX(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageTypeCtor getTypeCtor() {
    return findChildByClass(DLanguageTypeCtor.class);
  }

  @Override
  @Nullable
  public PsiElement getKwAuto() {
    return findChildByType(KW_AUTO);
  }

  @Override
  @Nullable
  public PsiElement getKwFinal() {
    return findChildByType(KW_FINAL);
  }

  @Override
  @Nullable
  public PsiElement getKwIn() {
    return findChildByType(KW_IN);
  }

  @Override
  @Nullable
  public PsiElement getKwLazy() {
    return findChildByType(KW_LAZY);
  }

  @Override
  @Nullable
  public PsiElement getKwOut() {
    return findChildByType(KW_OUT);
  }

  @Override
  @Nullable
  public PsiElement getKwRef() {
    return findChildByType(KW_REF);
  }

  @Override
  @Nullable
  public PsiElement getKwScope() {
    return findChildByType(KW_SCOPE);
  }

}
