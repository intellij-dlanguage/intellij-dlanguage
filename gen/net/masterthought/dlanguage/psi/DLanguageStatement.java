// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageStatement extends PsiElement {

  @Nullable
  DLanguageDoStatement getDoStatement();

  @Nullable
  DLanguageExpression getExpression();

  @Nullable
  DLanguageForStatement getForStatement();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageIfStatement getIfStatement();

  @Nullable
  DLanguageStatement getStatement();

  @Nullable
  DLanguageStatementBlock getStatementBlock();

  @Nullable
  DLanguageSwitchStatement getSwitchStatement();

  @Nullable
  DLanguageTryStatement getTryStatement();

  @Nullable
  DLanguageVariableDeclaration getVariableDeclaration();

  @Nullable
  DLanguageWhileStatement getWhileStatement();

}
