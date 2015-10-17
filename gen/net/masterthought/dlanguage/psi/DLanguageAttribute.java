// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageAttribute extends PsiElement {

  @Nullable
  DLanguageAlignAttribute getAlignAttribute();

  @Nullable
  DLanguageLinkageAttribute getLinkageAttribute();

  @Nullable
  DLanguagePragmaExpression getPragmaExpression();

  @Nullable
  DLanguageStorageClass getStorageClass();

  @Nullable
  PsiElement getKwExport();

  @Nullable
  PsiElement getKwPackage();

  @Nullable
  PsiElement getKwPrivate();

  @Nullable
  PsiElement getKwProtected();

  @Nullable
  PsiElement getKwPublic();

}
