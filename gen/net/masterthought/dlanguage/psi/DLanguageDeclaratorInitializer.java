// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageDeclaratorInitializerStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguageDeclaratorInitializer extends DNamedElement, VariableDeclaration, StubBasedPsiElement<DLanguageDeclaratorInitializerStub> {

  @Nullable
  DLanguageAltDeclarator getAltDeclarator();

  @Nullable
  DLanguageInitializer getInitializer();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @Nullable
  DLanguageVarDeclarator getVarDeclarator();

  @Nullable
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

  boolean actuallyIsDeclaration();

  DLanguageType getDeclarationType();

}
