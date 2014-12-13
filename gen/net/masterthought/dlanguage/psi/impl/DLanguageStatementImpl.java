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

public class DLanguageStatementImpl extends ASTWrapperPsiElement implements DLanguageStatement {

  public DLanguageStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitStatement(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageDoStatement getDoStatement() {
    return findChildByClass(DLanguageDoStatement.class);
  }

  @Override
  @Nullable
  public DLanguageExpression getExpression() {
    return findChildByClass(DLanguageExpression.class);
  }

  @Override
  @Nullable
  public DLanguageForStatement getForStatement() {
    return findChildByClass(DLanguageForStatement.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageIfStatement getIfStatement() {
    return findChildByClass(DLanguageIfStatement.class);
  }

  @Override
  @Nullable
  public DLanguageStatement getStatement() {
    return findChildByClass(DLanguageStatement.class);
  }

  @Override
  @Nullable
  public DLanguageStatementBlock getStatementBlock() {
    return findChildByClass(DLanguageStatementBlock.class);
  }

  @Override
  @Nullable
  public DLanguageSwitchStatement getSwitchStatement() {
    return findChildByClass(DLanguageSwitchStatement.class);
  }

  @Override
  @Nullable
  public DLanguageTryStatement getTryStatement() {
    return findChildByClass(DLanguageTryStatement.class);
  }

  @Override
  @Nullable
  public DLanguageVariableDeclaration getVariableDeclaration() {
    return findChildByClass(DLanguageVariableDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageWhileStatement getWhileStatement() {
    return findChildByClass(DLanguageWhileStatement.class);
  }

}
