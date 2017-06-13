// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageStatement extends PsiElement {

  @Nullable
  DLanguageAsmStatement getAsmStatement();

  @Nullable
  DLanguageBlockStatement getBlockStatement();

  @Nullable
  DLanguageBreakStatement getBreakStatement();

  @Nullable
  DLanguageCaseRangeStatement getCaseRangeStatement();

  @Nullable
  DLanguageCaseStatement getCaseStatement();

  @Nullable
  DLanguageConditionalStatement getConditionalStatement();

  @Nullable
  DLanguageContinueStatement getContinueStatement();

  @Nullable
  DLanguageDeclaration getDeclaration();

  @Nullable
  DLanguageDeclarationStatement getDeclarationStatement();

  @Nullable
  DLanguageDefaultStatement getDefaultStatement();

  @Nullable
  DLanguageDoStatement getDoStatement();

  @Nullable
  DLanguageExpressionStatement getExpressionStatement();

  @Nullable
  DLanguageFinalSwitchStatement getFinalSwitchStatement();

  @Nullable
  DLanguageForStatement getForStatement();

  @Nullable
  DLanguageForeachRangeStatement getForeachRangeStatement();

  @Nullable
  DLanguageForeachStatement getForeachStatement();

  @Nullable
  DLanguageGotoStatement getGotoStatement();

  @Nullable
  DLanguageIfStatement getIfStatement();

  @Nullable
  DLanguageImportDeclaration getImportDeclaration();

  @Nullable
  DLanguageLabeledStatement getLabeledStatement();

  @Nullable
  DLanguageMixinStatement getMixinStatement();

  @Nullable
  DLanguagePragmaStatement getPragmaStatement();

  @Nullable
  DLanguageReturnStatement getReturnStatement();

  @Nullable
  DLanguageScopeGuardStatement getScopeGuardStatement();

  @Nullable
  DLanguageStaticAssert getStaticAssert();

  @Nullable
  DLanguageSwitchStatement getSwitchStatement();

  @Nullable
  DLanguageSynchronizedStatement getSynchronizedStatement();

  @Nullable
  DLanguageTemplateMixin getTemplateMixin();

  @Nullable
  DLanguageThrowStatement getThrowStatement();

  @Nullable
  DLanguageTryStatement getTryStatement();

  @Nullable
  DLanguageWhileStatement getWhileStatement();

  @Nullable
  DLanguageWithStatement getWithStatement();

  @Nullable
  PsiElement getOpScolon();

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageStatement, ...)
  //methods are not found in DPsiImplUtil

}
