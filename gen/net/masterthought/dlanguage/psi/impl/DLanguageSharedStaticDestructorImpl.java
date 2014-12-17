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

public class DLanguageSharedStaticDestructorImpl extends ASTWrapperPsiElement implements DLanguageSharedStaticDestructor {

  public DLanguageSharedStaticDestructorImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitSharedStaticDestructor(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageFunctionBody getFunctionBody() {
    return findNotNullChildByClass(DLanguageFunctionBody.class);
  }

  @Override
  @NotNull
  public PsiElement getLParen() {
    return findNotNullChildByType(LPAREN);
  }

  @Override
  @NotNull
  public PsiElement getRParen() {
    return findNotNullChildByType(RPAREN);
  }

  @Override
  @NotNull
  public PsiElement getShared() {
    return findNotNullChildByType(SHARED);
  }

  @Override
  @NotNull
  public PsiElement getStatic() {
    return findNotNullChildByType(STATIC);
  }

  @Override
  @NotNull
  public PsiElement getThis() {
    return findNotNullChildByType(THIS);
  }

  @Override
  @NotNull
  public PsiElement getTilde() {
    return findNotNullChildByType(TILDE);
  }

}
