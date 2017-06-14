// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.VariableDeclaration;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageConditionVariableDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguageConditionVariableDeclaration extends DNamedElement, VariableDeclaration, Declaration, StubBasedPsiElement<DLanguageConditionVariableDeclarationStub> {

  @Nullable
  DLanguageBasicType getBasicType();

  @NotNull
  DLanguageCommaExpression getCommaExpression();

  @Nullable
  DLanguageDeclarator getDeclarator();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageTypeCtors getTypeCtors();

  @Nullable
  PsiElement getKwAuto();

  @NotNull
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

  //WARNING: processDeclarations(...) is skipped
  //matching processDeclarations(DLanguageConditionVariableDeclaration, ...)
  //methods are not found in DPsiImplUtil

}
