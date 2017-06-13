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

public class DLanguageScopeStatementImpl extends ASTWrapperPsiElement implements DLanguageScopeStatement {

  public DLanguageScopeStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitScopeStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAsmStatement getAsmStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAsmStatement.class);
  }

  @Override
  @Nullable
  public DLanguageBlockStatement getBlockStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBlockStatement.class);
  }

  @Override
  @Nullable
  public DLanguageBreakStatement getBreakStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageBreakStatement.class);
  }

  @Override
  @Nullable
  public DLanguageCaseRangeStatement getCaseRangeStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageCaseRangeStatement.class);
  }

  @Override
  @Nullable
  public DLanguageCaseStatement getCaseStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageCaseStatement.class);
  }

  @Override
  @Nullable
  public DLanguageConditionalStatement getConditionalStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageConditionalStatement.class);
  }

  @Override
  @Nullable
  public DLanguageContinueStatement getContinueStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageContinueStatement.class);
  }

  @Override
  @Nullable
  public DLanguageDeclaration getDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageDeclarationStatement getDeclarationStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclarationStatement.class);
  }

  @Override
  @Nullable
  public DLanguageDefaultStatement getDefaultStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDefaultStatement.class);
  }

  @Override
  @Nullable
  public DLanguageDoStatement getDoStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDoStatement.class);
  }

  @Override
  @Nullable
  public DLanguageExpressionStatement getExpressionStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageExpressionStatement.class);
  }

  @Override
  @Nullable
  public DLanguageFinalSwitchStatement getFinalSwitchStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageFinalSwitchStatement.class);
  }

  @Override
  @Nullable
  public DLanguageForStatement getForStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageForStatement.class);
  }

  @Override
  @Nullable
  public DLanguageForeachRangeStatement getForeachRangeStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageForeachRangeStatement.class);
  }

  @Override
  @Nullable
  public DLanguageForeachStatement getForeachStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageForeachStatement.class);
  }

  @Override
  @Nullable
  public DLanguageGotoStatement getGotoStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageGotoStatement.class);
  }

  @Override
  @Nullable
  public DLanguageIfStatement getIfStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIfStatement.class);
  }

  @Override
  @Nullable
  public DLanguageImportDeclaration getImportDeclaration() {
    return PsiTreeUtil.getChildOfType(this, DLanguageImportDeclaration.class);
  }

  @Override
  @Nullable
  public DLanguageLabeledStatement getLabeledStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageLabeledStatement.class);
  }

  @Override
  @Nullable
  public DLanguageMixinStatement getMixinStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageMixinStatement.class);
  }

  @Override
  @Nullable
  public DLanguagePragmaStatement getPragmaStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguagePragmaStatement.class);
  }

  @Override
  @Nullable
  public DLanguageReturnStatement getReturnStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageReturnStatement.class);
  }

  @Override
  @Nullable
  public DLanguageScopeGuardStatement getScopeGuardStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageScopeGuardStatement.class);
  }

  @Override
  @Nullable
  public DLanguageStaticAssert getStaticAssert() {
    return PsiTreeUtil.getChildOfType(this, DLanguageStaticAssert.class);
  }

  @Override
  @Nullable
  public DLanguageSwitchStatement getSwitchStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageSwitchStatement.class);
  }

  @Override
  @Nullable
  public DLanguageSynchronizedStatement getSynchronizedStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageSynchronizedStatement.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateMixin getTemplateMixin() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTemplateMixin.class);
  }

  @Override
  @Nullable
  public DLanguageThrowStatement getThrowStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageThrowStatement.class);
  }

  @Override
  @Nullable
  public DLanguageTryStatement getTryStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageTryStatement.class);
  }

  @Override
  @Nullable
  public DLanguageWhileStatement getWhileStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageWhileStatement.class);
  }

  @Override
  @Nullable
  public DLanguageWithStatement getWithStatement() {
    return PsiTreeUtil.getChildOfType(this, DLanguageWithStatement.class);
  }

}
