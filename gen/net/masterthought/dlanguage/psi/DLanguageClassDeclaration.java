// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageClassDeclaration extends PsiElement {

  @Nullable
  DLanguageClassName getClassName();

  @NotNull
  DLanguageFieldDeclaration getFieldDeclaration();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  List<DLanguageInterfaceName> getInterfaceNameList();

  @NotNull
  DLanguageModifier getModifier();

}
