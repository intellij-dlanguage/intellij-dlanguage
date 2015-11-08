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

public class DLanguageCatchImpl extends ASTWrapperPsiElement implements DLanguageCatch {

  public DLanguageCatchImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitCatch(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageCatchParameter getCatchParameter() {
    return findNotNullChildByClass(DLanguageCatchParameter.class);
  }

  @Override
  @NotNull
  public DLanguageStatement getStatement() {
    return findNotNullChildByClass(DLanguageStatement.class);
  }

  @Override
  @NotNull
  public PsiElement getKwCatch() {
    return findNotNullChildByType(KW_CATCH);
  }

  @Override
  @NotNull
  public PsiElement getOpParLeft() {
    return findNotNullChildByType(OP_PAR_LEFT);
  }

  @Override
  @NotNull
  public PsiElement getOpParRight() {
    return findNotNullChildByType(OP_PAR_RIGHT);
  }

}
