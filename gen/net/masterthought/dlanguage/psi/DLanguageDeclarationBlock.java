// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageDeclarationBlock extends PsiElement {

  @Nullable
  DLanguageDeclDef getDeclDef();

  @Nullable
  DLanguageDeclDefs getDeclDefs();

  @Nullable
  PsiElement getOpBracesLeft();

  @Nullable
  PsiElement getOpBracesRight();

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageDeclarationBlock, ...)
  //methods are not found in DPsiImplUtil

}
