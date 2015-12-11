// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
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
