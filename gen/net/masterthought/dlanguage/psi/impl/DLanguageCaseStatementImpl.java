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

public class DLanguageCaseStatementImpl extends ASTWrapperPsiElement implements DLanguageCaseStatement {

  public DLanguageCaseStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitCaseStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageArgumentList getArgumentList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageArgumentList.class);
  }

  @Override
  @Nullable
  public DLanguageScopeStatementList getScopeStatementList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageScopeStatementList.class);
  }

  @Override
  @NotNull
  public PsiElement getKwCase() {
    return notNullChild(findChildByType(KW_CASE));
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

}
