// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageLiteralExpression extends PsiElement {

  @Nullable
  DLanguageCharacter getCharacter();

  @Nullable
  DLanguageFloatLiteral getFloatLiteral();

  @Nullable
  DLanguageIntegerLiteral getIntegerLiteral();

  @Nullable
  DLanguageString getString();

}
