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

public class DLanguageLabeledStatementImpl extends ASTWrapperPsiElement implements DLanguageLabeledStatement {

  public DLanguageLabeledStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitLabeledStatement(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageNoScopeStatement getNoScopeStatement() {
    return findChildByClass(DLanguageNoScopeStatement.class);
  }

  @Override
  @Nullable
  public DLanguageStatement getStatement() {
    return findChildByClass(DLanguageStatement.class);
  }

  @Override
  @NotNull
  public PsiElement getOpColon() {
    return findNotNullChildByType(OP_COLON);
  }

}
