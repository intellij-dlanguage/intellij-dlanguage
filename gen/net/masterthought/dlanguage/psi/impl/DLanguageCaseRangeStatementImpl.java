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

public class DLanguageCaseRangeStatementImpl extends ASTWrapperPsiElement implements DLanguageCaseRangeStatement {

  public DLanguageCaseRangeStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitCaseRangeStatement(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<DLanguageAssignExpression> getAssignExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAssignExpression.class);
  }

  @Override
  @NotNull
  public DLanguageDeclarationsAndStatements getDeclarationsAndStatements() {
    return findNotNullChildByClass(DLanguageDeclarationsAndStatements.class);
  }

  @Override
  @NotNull
  public PsiElement getOpTripledot() {
    return findNotNullChildByType(OP_TRIPLEDOT);
  }

}
