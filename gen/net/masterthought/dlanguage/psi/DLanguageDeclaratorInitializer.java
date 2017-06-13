// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageDeclaratorInitializerStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;
import net.masterthought.dlanguage.psi.interfaces.Type;

public interface DLanguageDeclaratorInitializer extends DNamedElement, VariableDeclaration, Declaration, StubBasedPsiElement<DLanguageDeclaratorInitializerStub> {

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

  String getFullName();

  @Nullable
  PsiElement getNameIdentifier();

  @NotNull
  PsiReference getReference();

  @Nullable
  PsiElement setName(String newName);

  @NotNull
  ItemPresentation getPresentation();

  boolean isSomeVisibility(Visibility visibility, Class<? extends Container> containerType);

  boolean isSomeVisibility(Visibility visibility);

  boolean actuallyIsDeclaration();

  Type getVariableDeclarationType();

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageDeclaratorInitializer, ...)
  //methods are not found in DPsiImplUtil

}
