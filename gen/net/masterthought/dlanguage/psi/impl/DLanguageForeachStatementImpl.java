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

public class DLanguageForeachStatementImpl extends ASTWrapperPsiElement implements DLanguageForeachStatement {

  public DLanguageForeachStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitForeachStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageForeach getForeach() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageForeach.class));
  }

  @Override
  @NotNull
  public DLanguageForeachAggregate getForeachAggregate() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageForeachAggregate.class));
  }

  @Override
  @NotNull
  public DLanguageForeachTypeList getForeachTypeList() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageForeachTypeList.class));
  }

  @Override
  @NotNull
  public DLanguageStatement getStatement() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageStatement.class));
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

  public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
    return DPsiImplUtil.processDeclarations(this, processor, state, lastParent, place);
  }

}
