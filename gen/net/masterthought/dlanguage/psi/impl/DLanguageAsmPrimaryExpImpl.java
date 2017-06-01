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

public class DLanguageAsmPrimaryExpImpl extends ASTWrapperPsiElement implements DLanguageAsmPrimaryExp {

  public DLanguageAsmPrimaryExpImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAsmPrimaryExp(this);
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
  @Nullable
  public DLanguageDotIdentifier getDotIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDotIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageFloatLiteral getFloatLiteral() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFloatLiteral.class);
  }

  @Override
  @Nullable
  public DLanguageRegister getRegister() {
    return PsiTreeUtil.getChildOfType(this, DLanguageRegister.class);
  }

  @Override
  @Nullable
  public DLanguageRegister64 getRegister64() {
    return PsiTreeUtil.getChildOfType(this, DLanguageRegister64.class);
  }

  @Override
  @Nullable
  public DLanguageStringLiteral getStringLiteral() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStringLiteral.class);
  }

  @Override
  @Nullable
  public PsiElement getIntegerLiteral() {
    return findChildByType(INTEGER_LITERAL);
  }

  @Override
  @Nullable
  public PsiElement getKwThis() {
    return findChildByType(KW_THIS);
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

  @Override
  @Nullable
  public PsiElement getOpDollar() {
    return findChildByType(OP_DOLLAR);
  }

}
