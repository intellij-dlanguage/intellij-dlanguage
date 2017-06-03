// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageParameter extends PsiElement {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageDeclarator getDeclarator();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageInOut getInOut();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getKwAlias();

  @Nullable
  PsiElement getOpEq();

  @Nullable
  PsiElement getOpTripledot();

}
