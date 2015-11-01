// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
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
