// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAsmInstruction extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageAsmExp getAsmExp();

  @Nullable
  DLanguageAsmInstruction getAsmInstruction();

  @Nullable
  DLanguageOperands getOperands();

  @Nullable
  PsiElement getIntegerLiteral();

  @Nullable
  PsiElement getKwAlign();

  @Nullable
  PsiElement getOpColon();

}
