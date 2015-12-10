// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageImportBind extends PsiElement {

  @NotNull
  List<DLanguageIdentifier> getIdentifierList();

  @Nullable
  PsiElement getOpEq();

}
