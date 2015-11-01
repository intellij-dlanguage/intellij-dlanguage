// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAutoDeclarationX extends PsiElement {

  @Nullable
  DLanguageAutoDeclarationX getAutoDeclarationX();

  @NotNull
  List<DLanguageIdentifier> getIdentifierList();

  @NotNull
  List<DLanguageInitializer> getInitializerList();

  @NotNull
  List<DLanguageTemplateParameters> getTemplateParametersList();

  @Nullable
  PsiElement getOpComma();

}
