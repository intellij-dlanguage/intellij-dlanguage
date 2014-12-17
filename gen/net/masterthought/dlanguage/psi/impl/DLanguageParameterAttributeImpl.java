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

public class DLanguageParameterAttributeImpl extends ASTWrapperPsiElement implements DLanguageParameterAttribute {

  public DLanguageParameterAttributeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitParameterAttribute(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageTypeConstructor getTypeConstructor() {
    return findChildByClass(DLanguageTypeConstructor.class);
  }

  @Override
  @Nullable
  public PsiElement getAuto() {
    return findChildByType(AUTO);
  }

  @Override
  @Nullable
  public PsiElement getFinal() {
    return findChildByType(FINAL);
  }

  @Override
  @Nullable
  public PsiElement getIn() {
    return findChildByType(IN);
  }

  @Override
  @Nullable
  public PsiElement getLazy() {
    return findChildByType(LAZY);
  }

  @Override
  @Nullable
  public PsiElement getOut() {
    return findChildByType(OUT);
  }

  @Override
  @Nullable
  public PsiElement getRef() {
    return findChildByType(REF);
  }

  @Override
  @Nullable
  public PsiElement getScope() {
    return findChildByType(SCOPE);
  }

}
