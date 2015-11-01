// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateAliasParameter extends PsiElement {

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageDeclarator getDeclarator();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageTemplateAliasParameterDefault getTemplateAliasParameterDefault();

  @Nullable
  DLanguageTemplateAliasParameterSpecialization getTemplateAliasParameterSpecialization();

  @NotNull
  PsiElement getKwAlias();

}
