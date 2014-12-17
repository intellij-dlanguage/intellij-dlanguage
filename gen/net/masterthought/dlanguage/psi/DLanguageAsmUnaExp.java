// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAsmUnaExp extends PsiElement {

  @Nullable
  DLanguageAsmExp getAsmExp();

  @Nullable
  DLanguageAsmPrimaryExp getAsmPrimaryExp();

  @Nullable
  DLanguageAsmTypePrefix getAsmTypePrefix();

  @Nullable
  DLanguageAsmUnaExp getAsmUnaExp();

  @Nullable
  PsiElement getMinus();

  @Nullable
  PsiElement getNot();

  @Nullable
  PsiElement getPlus();

  @Nullable
  PsiElement getTilde();

}
