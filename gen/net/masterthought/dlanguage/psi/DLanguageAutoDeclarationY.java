// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageAutoDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageAutoDeclarationY extends DNamedElement, VariableDeclaration, StubBasedPsiElement<DLanguageAutoDeclarationStub> {

  @NotNull
  DLanguageIdentifier getIdentifier();

  @NotNull
  DLanguageInitializer getInitializer();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @NotNull
  PsiElement getOpEq();

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

  boolean actuallyIsDeclaration();

  DLanguageType getVariableDeclarationType();

}
