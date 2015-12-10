// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAltDeclaratorIdentifier extends PsiElement {

  @Nullable
  DLanguageAltDeclaratorSuffixes getAltDeclaratorSuffixes();

  @Nullable
  DLanguageBasicType2 getBasicType2();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageInitializer getInitializer();

  @Nullable
  PsiElement getOpEq();

}
