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

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAsmPrimaryExp(this);
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
  public DLanguageIdentifierChain getIdentifierChain() {
    return findChildByClass(DLanguageIdentifierChain.class);
  }

  @Override
  @Nullable
  public DLanguageRegister getRegister() {
    return findChildByClass(DLanguageRegister.class);
  }

  @Override
  @Nullable
  public PsiElement getDollar() {
    return findChildByType(DOLLAR);
  }

}
