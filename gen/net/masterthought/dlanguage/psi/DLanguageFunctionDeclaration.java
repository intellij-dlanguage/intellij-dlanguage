// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageFunctionDeclaration extends PsiElement {

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageConstraint getConstraint();

  @Nullable
  DLanguageFunctionBody getFunctionBody();

  @NotNull
  List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributeList();

  @NotNull
  DLanguageParameters getParameters();

  @NotNull
  List<DLanguageStorageClass> getStorageClassList();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getOpScolon();

}
