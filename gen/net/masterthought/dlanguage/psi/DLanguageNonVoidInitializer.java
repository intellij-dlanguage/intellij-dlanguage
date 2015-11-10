// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageNonVoidInitializer extends PsiElement {

  @Nullable
  DLanguageArrayInitializer getArrayInitializer();

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageStructInitializer getStructInitializer();

}
