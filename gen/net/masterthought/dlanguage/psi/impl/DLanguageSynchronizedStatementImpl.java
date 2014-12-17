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

public class DLanguageSynchronizedStatementImpl extends ASTWrapperPsiElement implements DLanguageSynchronizedStatement {

  public DLanguageSynchronizedStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitSynchronizedStatement(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageExpression getExpression() {
    return findChildByClass(DLanguageExpression.class);
  }

  @Override
  @NotNull
  public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault() {
    return findNotNullChildByClass(DLanguageStatementNoCaseNoDefault.class);
  }

  @Override
  @Nullable
  public PsiElement getLParen() {
    return findChildByType(LPAREN);
  }

  @Override
  @Nullable
  public PsiElement getRParen() {
    return findChildByType(RPAREN);
  }

  @Override
  @NotNull
  public PsiElement getSynchronized() {
    return findNotNullChildByType(SYNCHRONIZED);
  }

}
