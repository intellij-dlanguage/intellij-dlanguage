// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.containers.StatementContainer;
import net.masterthought.dlanguage.psi.interfaces.containers.MixinContainer;
import net.masterthought.dlanguage.psi.interfaces.containers.GlobalDeclarationContainer;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.HasTemplateArguments;
import net.masterthought.dlanguage.psi.interfaces.Mixinable;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageTemplateDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageTemplateDeclaration extends DNamedElement, StatementContainer, MixinContainer, GlobalDeclarationContainer, HasVisibility, HasTemplateArguments, Mixinable, Declaration, StubBasedPsiElement<DLanguageTemplateDeclarationStub> {

  @Nullable
  DLanguageConstraint getConstraint();

  @Nullable
  DLanguageDeclDefs getDeclDefs();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  DLanguageTemplateParameters getTemplateParameters();

  @NotNull
  PsiElement getKwTemplate();

  @NotNull
  PsiElement getOpBracesLeft();

  @NotNull
  PsiElement getOpBracesRight();

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
