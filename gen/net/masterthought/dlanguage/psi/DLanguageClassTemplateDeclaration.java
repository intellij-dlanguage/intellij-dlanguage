// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageClassTemplateDeclaration extends PsiElement {

  @NotNull
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageBaseClassList getBaseClassList();

  @Nullable
  DLanguageConstraint getConstraint();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  DLanguageTemplateParameters getTemplateParameters();

  @NotNull
  PsiElement getKwClass();

}
