// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAsmStatement extends PsiElement {

  @Nullable
  DLanguageAsmInstructionList getAsmInstructionList();

  @Nullable
  DLanguageFunctionAttributes getFunctionAttributes();

  @NotNull
  PsiElement getKwAsm();

  @Nullable
  PsiElement getOpBracesLeft();

  @Nullable
  PsiElement getOpBracesRight();

}
