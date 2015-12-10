// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageIdentifierList extends PsiElement {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageIdentifierList getIdentifierList();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @Nullable
  PsiElement getOpBracketLeft();

  @Nullable
  PsiElement getOpBracketRight();

  @Nullable
  PsiElement getOpDot();

}
