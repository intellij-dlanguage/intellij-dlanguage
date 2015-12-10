// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageUserDefinedAttribute extends PsiElement {

  @Nullable
  DLanguageArgumentList getArgumentList();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @NotNull
  PsiElement getOpAt();

  @Nullable
  PsiElement getOpParLeft();

  @Nullable
  PsiElement getOpParRight();

}
