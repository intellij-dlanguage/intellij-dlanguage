// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageConstructor extends PsiElement {

  @Nullable
  DLanguageConstructorTemplate getConstructorTemplate();

  @Nullable
  DLanguageFunctionBody getFunctionBody();

  @Nullable
  DLanguageMemberFunctionAttributes getMemberFunctionAttributes();

  @Nullable
  DLanguageParameters getParameters();

  @Nullable
  PsiElement getKwThis();

  @Nullable
  PsiElement getOpScolon();

}
