// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageSymbol;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateArgument extends PsiElement {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageSymbol getSymbol();

  @Nullable
  DLanguageType getType();

}
