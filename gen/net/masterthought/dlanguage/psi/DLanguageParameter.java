// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageParameterStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageParameter extends DNamedElement, Declaration, VariableDeclaration, StubBasedPsiElement<DLanguageParameterStub> {

  @Nullable
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageDeclarator getDeclarator();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageInOut getInOut();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getKwAlias();

  @Nullable
  PsiElement getOpEq();

  @Nullable
  PsiElement getOpTripledot();

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

  boolean isSomeVisibility(Visibility visibility, Class<? extends Container> containerType);

  boolean isSomeVisibility(Visibility visibility);

}
