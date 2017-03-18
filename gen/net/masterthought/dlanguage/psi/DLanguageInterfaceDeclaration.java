// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.containers.StatementContainer;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.HasTemplateArguments;
import net.masterthought.dlanguage.psi.interfaces.CanInherit;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageInterfaceDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import java.util.Map;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageInterfaceDeclaration extends StatementContainer, DNamedElement, HasVisibility, HasTemplateArguments, CanInherit, StubBasedPsiElement<DLanguageInterfaceDeclarationStub> {

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

  boolean isSomeVisibility(Visibility visibility);

  List<CanInherit> whatInheritsFrom();

  List<DLanguageTemplateParameter> getTemplateArguments();

  Map<String, DLanguageIdentifier> getSuperClassNames();

}
