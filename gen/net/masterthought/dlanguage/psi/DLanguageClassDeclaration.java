// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface DLanguageClassDeclaration extends PsiElement {

  @Nullable
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageBaseClassList getBaseClassList();

  @Nullable
  DLanguageClassTemplateDeclaration getClassTemplateDeclaration();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  PsiElement getKwClass();

  @Nullable
  PsiElement getOpScolon();

}
