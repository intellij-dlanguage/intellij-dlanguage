// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
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
