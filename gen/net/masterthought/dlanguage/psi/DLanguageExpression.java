// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageExpression extends PsiElement {

  @Nullable
  DLanguageArglist getArglist();

  @Nullable
  DLanguageBitExpression getBitExpression();

  @Nullable
  DLanguageCastingExpression getCastingExpression();

  @Nullable
  DLanguageClassName getClassName();

  @Nullable
  DLanguageCreatingExpression getCreatingExpression();

  @Nullable
  DLanguageExpression getExpression();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageInterfaceName getInterfaceName();

  @Nullable
  DLanguageLiteralExpression getLiteralExpression();

  @Nullable
  DLanguageLogicalExpression getLogicalExpression();

  @Nullable
  DLanguageNumericExpression getNumericExpression();

  @Nullable
  DLanguageStringExpression getStringExpression();

  @Nullable
  DLanguageTestingExpression getTestingExpression();

}
