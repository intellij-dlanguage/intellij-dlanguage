// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageTemplateTypeParameter extends PsiElement {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageTemplateValueParameterDefault getTemplateValueParameterDefault();

  @NotNull
  List<DLanguageType> getTypeList();

  @Nullable
  PsiElement getOpEq();

  @Nullable
  PsiElement getOpTripledot();

}
