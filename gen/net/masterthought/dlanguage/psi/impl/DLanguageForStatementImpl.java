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

public class DLanguageForStatementImpl extends ASTWrapperPsiElement implements DLanguageForStatement {

  public DLanguageForStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitForStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageIncrement getIncrement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIncrement.class);
  }

  @Override
  @NotNull
  public DLanguageInitialize getInitialize() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageInitialize.class));
  }

  @Override
  @NotNull
  public DLanguageScopeStatement getScopeStatement() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageScopeStatement.class));
  }

  @Override
  @Nullable
  public DLanguageTest getTest() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTest.class);
  }

  @Override
  @NotNull
  public PsiElement getKwFor() {
    return notNullChild(findChildByType(KW_FOR));
  }

  @Override
  @NotNull
  public PsiElement getOpParLeft() {
    return notNullChild(findChildByType(OP_PAR_LEFT));
  }

  @Override
  @NotNull
  public PsiElement getOpParRight() {
    return notNullChild(findChildByType(OP_PAR_RIGHT));
  }

  @Override
  @NotNull
  public PsiElement getOpScolon() {
    return notNullChild(findChildByType(OP_SCOLON));
  }

}
