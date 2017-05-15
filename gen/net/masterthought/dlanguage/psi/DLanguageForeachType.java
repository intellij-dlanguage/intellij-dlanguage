// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageForeachType extends PsiElement {

  @Nullable
  DLanguageForeachTypeAttribute getForeachTypeAttribute();

  @Nullable
  DLanguageForeachTypeAttributes getForeachTypeAttributes();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageType getType();

}
