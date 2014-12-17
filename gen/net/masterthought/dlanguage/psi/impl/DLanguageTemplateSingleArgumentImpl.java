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

public class DLanguageTemplateSingleArgumentImpl extends ASTWrapperPsiElement implements DLanguageTemplateSingleArgument {

  public DLanguageTemplateSingleArgumentImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitTemplateSingleArgument(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageFloatLiteral getFloatLiteral() {
    return findChildByClass(DLanguageFloatLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageIntegerLiteral getIntegerLiteral() {
    return findChildByClass(DLanguageIntegerLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageBuiltinType getBuiltinType() {
    return findChildByClass(DLanguageBuiltinType.class);
  }

  @Override
  @Nullable
  public PsiElement getCharacterLiteral() {
    return findChildByType(CHARACTERLITERAL);
  }

  @Override
  @Nullable
  public PsiElement getFalse() {
    return findChildByType(FALSE);
  }

  @Override
  @Nullable
  public PsiElement getNull() {
    return findChildByType(NULL);
  }

  @Override
  @Nullable
  public PsiElement getSpecialDate() {
    return findChildByType(SPECIALDATE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialFile() {
    return findChildByType(SPECIALFILE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialFunction() {
    return findChildByType(SPECIALFUNCTION);
  }

  @Override
  @Nullable
  public PsiElement getSpecialLine() {
    return findChildByType(SPECIALLINE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialModule() {
    return findChildByType(SPECIALMODULE);
  }

  @Override
  @Nullable
  public PsiElement getSpecialPrettyFunction() {
    return findChildByType(SPECIALPRETTYFUNCTION);
  }

  @Override
  @Nullable
  public PsiElement getSpecialTime() {
    return findChildByType(SPECIALTIME);
  }

  @Override
  @Nullable
  public PsiElement getSpecialVendor() {
    return findChildByType(SPECIALVENDOR);
  }

  @Override
  @Nullable
  public PsiElement getSpecialVersion() {
    return findChildByType(SPECIALVERSION);
  }

  @Override
  @Nullable
  public PsiElement getSpecialimestamp() {
    return findChildByType(SPECIALIMESTAMP);
  }

  @Override
  @Nullable
  public PsiElement getThis() {
    return findChildByType(THIS);
  }

  @Override
  @Nullable
  public PsiElement getTrue() {
    return findChildByType(TRUE);
  }

}
