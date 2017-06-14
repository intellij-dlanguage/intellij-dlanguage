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
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;

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
  @Nullable
  public DLanguageInitialize getInitialize() {
    return PsiTreeUtil.getChildOfType(this, DLanguageInitialize.class);
  }

  @Override
  @Nullable
  public DLanguageScopeStatement getScopeStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageScopeStatement.class);
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
  @Nullable
  public PsiElement getOpParLeft() {
    return findChildByType(OP_PAR_LEFT);
  }

  @Override
  @Nullable
  public PsiElement getOpParRight() {
    return findChildByType(OP_PAR_RIGHT);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

  public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
    return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
  }

}
