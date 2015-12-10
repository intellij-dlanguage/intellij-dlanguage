// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageStructDeclaration extends PsiElement {

  @Nullable
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageConstraint getConstraint();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @NotNull
  PsiElement getKwStruct();

  @Nullable
  PsiElement getOpScolon();

}
