// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAsmPrimaryExp extends PsiElement {

  @Nullable
  DLanguageAsmExp getAsmExp();

  @Nullable
  DLanguageDotIdentifier getDotIdentifier();

  @Nullable
  DLanguageRegister getRegister();

  @Nullable
  DLanguageRegister64 getRegister64();

  @Nullable
  DLanguageStringLiteral getStringLiteral();

  @Nullable
  PsiElement getFloatLiteral();

  @Nullable
  PsiElement getIntegerLiteral();

  @Nullable
  PsiElement getKwThis();

  @Nullable
  PsiElement getOpColon();

  @Nullable
  PsiElement getOpDollar();

}
