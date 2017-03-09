// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageClassDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguageClassDeclaration extends StatementContainer, DNamedElement, HasVisibility, HasTemplateArguments, HasArguments, CanInherit, StubBasedPsiElement<DLanguageClassDeclarationStub> {

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

  @NotNull
  String getName();

  @Nullable
  PsiElement getNameIdentifier();

  @NotNull
  PsiReference getReference();

  @Nullable
  PsiElement setName(String newName);

  @NotNull
  ItemPresentation getPresentation();

}
