// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageSymbol;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageWithStatement extends PsiElement {

  @Nullable
  DLanguageExpression getExpression();

  @NotNull
  DLanguageScopeStatement getScopeStatement();

  @Nullable
  DLanguageSymbol getSymbol();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @NotNull
  PsiElement getKwWith();

  @NotNull
  PsiElement getOpParLeft();

  @NotNull
  PsiElement getOpParRight();

}
