// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageFloatLiteral extends PsiElement {

  @NotNull
  List<DLanguageDecimalDigits> getDecimalDigitsList();

  @Nullable
  DLanguageExponentPart getExponentPart();

  @Nullable
  DLanguageFloatTypeSuffix getFloatTypeSuffix();

}
