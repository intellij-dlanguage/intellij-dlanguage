// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTypeInferredParameterList extends PsiElement {

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageMemberFunctionAttributes getMemberFunctionAttributes();

  @Nullable
  DLanguageStorageClass getStorageClass();

  @Nullable
  DLanguageTypeCtor getTypeCtor();

  @Nullable
  DLanguageTypeInferredParameterList getTypeInferredParameterList();

  @Nullable
  PsiElement getOpComma();

}
