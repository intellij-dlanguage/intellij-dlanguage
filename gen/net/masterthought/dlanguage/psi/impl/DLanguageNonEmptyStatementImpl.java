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

public class DLanguageNonEmptyStatementImpl extends ASTWrapperPsiElement implements DLanguageNonEmptyStatement {

  public DLanguageNonEmptyStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitNonEmptyStatement(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageCaseRangeStatement getCaseRangeStatement() {
    return findChildByClass(DLanguageCaseRangeStatement.class);
  }

  @Override
  @Nullable
  public DLanguageCaseStatement getCaseStatement() {
    return findChildByClass(DLanguageCaseStatement.class);
  }

  @Override
  @Nullable
  public DLanguageDefaultStatement getDefaultStatement() {
    return findChildByClass(DLanguageDefaultStatement.class);
  }

  @Override
  @Nullable
  public DLanguageNonEmptyStatementNoCaseNoDefault getNonEmptyStatementNoCaseNoDefault() {
    return findChildByClass(DLanguageNonEmptyStatementNoCaseNoDefault.class);
  }

}
