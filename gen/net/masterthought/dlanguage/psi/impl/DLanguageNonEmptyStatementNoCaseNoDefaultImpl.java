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

public class DLanguageNonEmptyStatementNoCaseNoDefaultImpl extends ASTWrapperPsiElement implements DLanguageNonEmptyStatementNoCaseNoDefault {

  public DLanguageNonEmptyStatementNoCaseNoDefaultImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitNonEmptyStatementNoCaseNoDefault(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAsmStatement getAsmStatement() {
    return findChildByClass(DLanguageAsmStatement.class);
  }

  @Override
  @Nullable
  public DLanguageBreakStatement getBreakStatement() {
    return findChildByClass(DLanguageBreakStatement.class);
  }

  @Override
  @Nullable
  public DLanguageConditionalStatement getConditionalStatement() {
    return findChildByClass(DLanguageConditionalStatement.class);
  }

  @Override
  @Nullable
  public DLanguageContinueStatement getContinueStatement() {
    return findChildByClass(DLanguageContinueStatement.class);
  }

  @Override
  @Nullable
  public DLanguageDeclarationStatement getDeclarationStatement() {
    return findChildByClass(DLanguageDeclarationStatement.class);
  }

  @Override
  @Nullable
  public DLanguageDoStatement getDoStatement() {
    return findChildByClass(DLanguageDoStatement.class);
  }

  @Override
  @Nullable
  public DLanguageExpressionStatement getExpressionStatement() {
    return findChildByClass(DLanguageExpressionStatement.class);
  }

  @Override
  @Nullable
  public DLanguageFinalSwitchStatement getFinalSwitchStatement() {
    return findChildByClass(DLanguageFinalSwitchStatement.class);
  }

  @Override
  @Nullable
  public DLanguageForStatement getForStatement() {
    return findChildByClass(DLanguageForStatement.class);
  }

  @Override
  @Nullable
  public DLanguageForeachRangeStatement getForeachRangeStatement() {
    return findChildByClass(DLanguageForeachRangeStatement.class);
  }

  @Override
  @Nullable
  public DLanguageForeachStatement getForeachStatement() {
    return findChildByClass(DLanguageForeachStatement.class);
  }

  @Override
  @Nullable
  public DLanguageGotoStatement getGotoStatement() {
    return findChildByClass(DLanguageGotoStatement.class);
  }

  @Override
  @Nullable
  public DLanguageIfStatement getIfStatement() {
    return findChildByClass(DLanguageIfStatement.class);
  }

  @Override
  @Nullable
  public DLanguageImportDeclaration getImportDeclaration() {
    return findChildByClass(DLanguageImportDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageLabeledStatement getLabeledStatement() {
    return findChildByClass(DLanguageLabeledStatement.class);
  }

  @Override
  @Nullable
  public DLanguageMixinStatement getMixinStatement() {
    return findChildByClass(DLanguageMixinStatement.class);
  }

  @Override
  @Nullable
  public DLanguagePragmaStatement getPragmaStatement() {
    return findChildByClass(DLanguagePragmaStatement.class);
  }

  @Override
  @Nullable
  public DLanguageReturnStatement getReturnStatement() {
    return findChildByClass(DLanguageReturnStatement.class);
  }

  @Override
  @Nullable
  public DLanguageScopeGuardStatement getScopeGuardStatement() {
    return findChildByClass(DLanguageScopeGuardStatement.class);
  }

  @Override
  @Nullable
  public DLanguageStaticAssert getStaticAssert() {
    return findChildByClass(DLanguageStaticAssert.class);
  }

  @Override
  @Nullable
  public DLanguageSwitchStatement getSwitchStatement() {
    return findChildByClass(DLanguageSwitchStatement.class);
  }

  @Override
  @Nullable
  public DLanguageSynchronizedStatement getSynchronizedStatement() {
    return findChildByClass(DLanguageSynchronizedStatement.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateMixin getTemplateMixin() {
    return findChildByClass(DLanguageTemplateMixin.class);
  }

  @Override
  @Nullable
  public DLanguageThrowStatement getThrowStatement() {
    return findChildByClass(DLanguageThrowStatement.class);
  }

  @Override
  @Nullable
  public DLanguageTryStatement getTryStatement() {
    return findChildByClass(DLanguageTryStatement.class);
  }

  @Override
  @Nullable
  public DLanguageWhileStatement getWhileStatement() {
    return findChildByClass(DLanguageWhileStatement.class);
  }

  @Override
  @Nullable
  public DLanguageWithStatement getWithStatement() {
    return findChildByClass(DLanguageWithStatement.class);
  }

}
