// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAsmShiftExp extends PsiElement {

  @NotNull
  DLanguageAsmAddExp getAsmAddExp();

  @Nullable
  DLanguageAsmShiftExp getAsmShiftExp();

  @Nullable
  PsiElement getOpShLeft();

  @Nullable
  PsiElement getOpShRight();

  @Nullable
  PsiElement getOpUshRight();

}
