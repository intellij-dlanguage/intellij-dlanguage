// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;
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

  boolean isSomeVisibility(String visibility);

  boolean actuallyIsDeclaration();

  DLanguageType getVariableDeclarationType();

}
