// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageTemplateDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguageTemplateDeclaration extends DNamedElement, StubBasedPsiElement<DLanguageTemplateDeclarationStub> {

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

  @Nullable
  PsiElement getNameIdentifier();

  @NotNull
  PsiReference getReference();

  @Nullable
  PsiElement setName(String newName);

  @NotNull
  ItemPresentation getPresentation();

}
