// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageConstructor extends PsiElement {

  @Nullable
  DLanguageConstraint getConstraint();

  @Nullable
  DLanguageFunctionBody getFunctionBody();

  @NotNull
  List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributeList();

  @NotNull
  DLanguageParameters getParameters();

  @NotNull
  DLanguageTemplateParameters getTemplateParameters();

  @NotNull
  PsiElement getKwThis();

  @Nullable
  PsiElement getOpScolon();

}
