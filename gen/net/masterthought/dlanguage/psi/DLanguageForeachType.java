// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageForeachTypeStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguageForeachType extends DNamedElement, VariableDeclaration, Declaration, StubBasedPsiElement<DLanguageForeachTypeStub> {

  @Nullable
  DLanguageForeachTypeAttribute getForeachTypeAttribute();

  @Nullable
  DLanguageForeachTypeAttributes getForeachTypeAttributes();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageType getType();

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

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageForeachType, ...)
  //methods are not found in DPsiImplUtil

}
