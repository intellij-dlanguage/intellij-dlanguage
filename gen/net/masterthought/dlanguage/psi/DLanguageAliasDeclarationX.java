// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAliasDeclarationX extends PsiElement {

  @Nullable
  DLanguageAliasDeclarationX getAliasDeclarationX();

  @NotNull
  List<DLanguageIdentifier> getIdentifierList();

  @NotNull
  List<DLanguageStorageClasses> getStorageClassesList();

  @NotNull
  List<DLanguageTemplateParameters> getTemplateParametersList();

  @NotNull
  List<DLanguageType> getTypeList();

  @Nullable
  PsiElement getOpComma();

}
