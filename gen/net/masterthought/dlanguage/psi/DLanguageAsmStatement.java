// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAsmStatement extends PsiElement {

  @NotNull
  List<DLanguageAsmInstruction> getAsmInstructionList();

  @NotNull
  PsiElement getAsm();

  @NotNull
  PsiElement getLBrace();

  @NotNull
  PsiElement getRBrace();

}
