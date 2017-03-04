// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageInterfaceDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguageInterfaceDeclaration extends StatementContainer, DNamedElement, HasVisibility, HasTemplateArguments, HasArguments, StubBasedPsiElement<DLanguageInterfaceDeclarationStub> {

  @Nullable
  DLanguageAggregateBody getAggregateBody();

  @Nullable
  DLanguageBaseInterfaceList getBaseInterfaceList();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageInterfaceTemplateDeclaration getInterfaceTemplateDeclaration();

  @Nullable
  PsiElement getKwInterface();

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
