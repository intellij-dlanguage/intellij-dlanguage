// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.containers.GlobalDeclarationContainer;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.containers.Declaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageModuleDeclaration extends DNamedElement, GlobalDeclarationContainer, HasVisibility, Declaration, StubBasedPsiElement<DLanguageModuleDeclarationStub> {

  @Nullable
  DLanguageAttribute getAttribute();

  @NotNull
  DLanguageModuleFullyQualifiedName getModuleFullyQualifiedName();

  @NotNull
  PsiElement getKwModule();

  @NotNull
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

}
