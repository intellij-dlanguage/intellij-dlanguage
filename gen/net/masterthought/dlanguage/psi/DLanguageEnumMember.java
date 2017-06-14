// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageEnumMemberStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageEnumMember extends DNamedElement, Declaration, StubBasedPsiElement<DLanguageEnumMemberStub> {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getOpEq();

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

  //WARNING: getTemplateArguments(...) is skipped
  //matching getTemplateArguments(DLanguageEnumMember, ...)
  //methods are not found in DPsiImplUtil

  boolean isSomeVisibility(Visibility visibility, Class<? extends Container> containerType);

  //WARNING: proccessDeclarations(...) is skipped
  //matching proccessDeclarations(DLanguageEnumMember, ...)
  //methods are not found in DPsiImplUtil

}
