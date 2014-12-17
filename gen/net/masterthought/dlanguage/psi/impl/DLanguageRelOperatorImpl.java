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

public class DLanguageRelOperatorImpl extends ASTWrapperPsiElement implements DLanguageRelOperator {

  public DLanguageRelOperatorImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitRelOperator(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getGreater() {
    return findChildByType(GREATER);
  }

  @Override
  @Nullable
  public PsiElement getGreaterEqual() {
    return findChildByType(GREATEREQUAL);
  }

  @Override
  @Nullable
  public PsiElement getLess() {
    return findChildByType(LESS);
  }

  @Override
  @Nullable
  public PsiElement getLessEqual() {
    return findChildByType(LESSEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getLessEqualGreater() {
    return findChildByType(LESSEQUALGREATER);
  }

  @Override
  @Nullable
  public PsiElement getLessOrGreater() {
    return findChildByType(LESSORGREATER);
  }

  @Override
  @Nullable
  public PsiElement getNotGreater() {
    return findChildByType(NOTGREATER);
  }

  @Override
  @Nullable
  public PsiElement getNotGreaterEqual() {
    return findChildByType(NOTGREATEREQUAL);
  }

  @Override
  @Nullable
  public PsiElement getNotLess() {
    return findChildByType(NOTLESS);
  }

  @Override
  @Nullable
  public PsiElement getNotLessEqual() {
    return findChildByType(NOTLESSEQUAL);
  }

  @Override
  @Nullable
  public PsiElement getNotLessEqualGreater() {
    return findChildByType(NOTLESSEQUALGREATER);
  }

  @Override
  @Nullable
  public PsiElement getUnordered() {
    return findChildByType(UNORDERED);
  }

}
