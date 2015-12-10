// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAltDeclarator extends PsiElement {

  @Nullable
  DLanguageAltDeclaratorSuffixes getAltDeclaratorSuffixes();

  @Nullable
  DLanguageAltDeclaratorX getAltDeclaratorX();

  @Nullable
  DLanguageAltFuncDeclaratorSuffix getAltFuncDeclaratorSuffix();

  @Nullable
  DLanguageBasicType2 getBasicType2();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

}
