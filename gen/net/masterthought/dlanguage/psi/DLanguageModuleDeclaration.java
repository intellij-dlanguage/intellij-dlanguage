// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguageModuleDeclaration extends DNamedElement, StubBasedPsiElement<DLanguageModuleDeclarationStub> {

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

  @Nullable
  PsiElement getNameIdentifier();

  @NotNull
  PsiReference getReference();

  @Nullable
  PsiElement setName(String newName);

  @NotNull
  ItemPresentation getPresentation();

  DLanguageProtectionAttribute getProtection();

}
