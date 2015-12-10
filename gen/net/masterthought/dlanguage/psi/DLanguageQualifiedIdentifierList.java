// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageQualifiedIdentifierList extends PsiElement {

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageQualifiedIdentifierList getQualifiedIdentifierList();

  @Nullable
  DLanguageTemplateInstance getTemplateInstance();

  @Nullable
  PsiElement getOpDot();

}
