// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageFunctionCallExpression extends PsiElement {

  @NotNull
  List<DLanguageArguments> getArgumentsList();

  @Nullable
  DLanguageSymbol getSymbol();

  @Nullable
  DLanguageType getType();

  @Nullable
  DLanguageUnaryExpression getUnaryExpression();

}
