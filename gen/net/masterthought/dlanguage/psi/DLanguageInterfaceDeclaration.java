// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageInterfaceDeclaration extends PsiElement {

  @NotNull
  DLanguageFieldDeclaration getFieldDeclaration();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  List<DLanguageInterfaceName> getInterfaceNameList();

  @NotNull
  DLanguageModifier getModifier();

}
