// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageParameter extends PsiElement {

  @Nullable
  DLanguageAssertExpression getAssertExpression();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageDeclarator getDeclarator();

  @Nullable
  DLanguageInOut getInOut();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getOpEq();

  @Nullable
  PsiElement getOpTripledot();

}
