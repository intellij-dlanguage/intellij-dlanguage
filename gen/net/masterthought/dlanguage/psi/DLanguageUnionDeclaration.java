// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.containers.StatementContainer;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.HasTemplateArguments;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageUnionDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageUnionDeclaration extends StatementContainer, DNamedElement, HasVisibility, HasTemplateArguments, StubBasedPsiElement<DLanguageUnionDeclarationStub> {

  @Nullable
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageAnonUnionDeclaration getAnonUnionDeclaration();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageUnionTemplateDeclaration getUnionTemplateDeclaration();

  @Nullable
  PsiElement getKwUnion();

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

  boolean isSomeVisibility(Visibility visibility);

}
