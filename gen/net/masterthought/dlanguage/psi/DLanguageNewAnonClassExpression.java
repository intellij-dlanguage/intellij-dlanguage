// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageNewAnonClassExpression extends PsiElement {

  @NotNull
  List<DLanguageArguments> getArgumentsList();

  @Nullable
  DLanguageBaseClassList getBaseClassList();

  @NotNull
  DLanguageStructBody getStructBody();

//  @NotNull
//  PsiElement getClass();

  @NotNull
  PsiElement getNew();

}
