// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;

import net.masterthought.dlanguage.psi.interfaces.*;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.containers.StatementContainer;
import net.masterthought.dlanguage.psi.interfaces.containers.MixinContainer;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageInterfaceDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import java.util.Map;

public interface DLanguageInterfaceDeclaration extends StatementContainer, DNamedElement, MixinContainer, HasVisibility, HasTemplateArguments, CanInherit, Declaration, StubBasedPsiElement<DLanguageInterfaceDeclarationStub> {

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

  String getFullName();

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
