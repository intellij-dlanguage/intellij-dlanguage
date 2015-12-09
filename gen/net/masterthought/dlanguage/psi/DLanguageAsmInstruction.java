// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAsmInstruction extends PsiElement {

  @Nullable
  DLanguageAsmInstruction getAsmInstruction();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageIntegerExpression getIntegerExpression();

  @Nullable
  DLanguageOpcode getOpcode();

  @Nullable
  DLanguageOperands getOperands();

  @Nullable
  PsiElement getKwAlign();

  @Nullable
  PsiElement getOpColon();

}
