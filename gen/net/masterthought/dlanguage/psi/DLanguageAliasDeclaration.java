// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAliasDeclaration extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @NotNull
  List<DLanguageAliasInitializer> getAliasInitializerList();

  @Nullable
  DLanguageLinkageAttribute getLinkageAttribute();

  @Nullable
  DLanguageType getType();

  @NotNull
  PsiElement getKwAlias();

  @NotNull
  PsiElement getOpScolon();

}
