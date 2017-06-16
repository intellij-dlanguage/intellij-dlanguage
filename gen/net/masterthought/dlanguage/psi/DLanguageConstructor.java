// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.containers.StatementContainer;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.HasTemplateParameters;
import net.masterthought.dlanguage.psi.interfaces.HasParameters;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageConstructorStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageConstructor extends StatementContainer, DNamedElement, HasVisibility, HasTemplateParameters, HasParameters, Declaration, StubBasedPsiElement<DLanguageConstructorStub> {

  @Nullable
  DLanguageConstraint getConstraint();

  @Nullable
  DLanguageFunctionBody getFunctionBody();

  @Nullable
  DLanguageMemberFunctionAttributes getMemberFunctionAttributes();

  @NotNull
  DLanguageParameters getParameters();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @NotNull
  PsiElement getKwThis();

  @Nullable
  PsiElement getOpScolon();

  @NotNull
  String getName();

  String getFullName();

  @Nullable
  PsiElement getNameIdentifier();

  @NotNull
  PsiReference getReference();

  @NotNull
  PsiElement setName(String newName);

  @NotNull
  ItemPresentation getPresentation();

  boolean isSomeVisibility(Visibility visibility, Class<? extends Container> containerType);

  boolean isSomeVisibility(Visibility visibility);

  @NotNull
  List<DLanguageParameter> getParameterList();

  boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place);

}
