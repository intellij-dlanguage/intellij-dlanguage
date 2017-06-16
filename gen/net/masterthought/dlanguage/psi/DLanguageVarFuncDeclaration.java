// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.Declaration;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageVarFuncDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import com.intellij.psi.ResolveState;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.psi.interfaces.containers.Container;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageVarFuncDeclaration extends DNamedElement, HasVisibility, Declaration, StubBasedPsiElement<DLanguageVarFuncDeclarationStub> {

  @NotNull
  DLanguageAssignExpression getAssignExpression();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageBasicType2 getBasicType2();

  @Nullable
  DLanguageConstraint getConstraint();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageMemberFunctionAttributes getMemberFunctionAttributes();

  @NotNull
  DLanguageParameters getParameters();

  @Nullable
  DLanguageTemplateParameters getTemplateParameters();

  @Nullable
  DLanguageType getType();

  @Nullable
  PsiElement getKwAuto();

  @Nullable
  PsiElement getKwEnum();

  @NotNull
  PsiElement getOpEq();

  @NotNull
  PsiElement getOpScolon();

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

  //WARNING: actuallyIsDeclaration(...) is skipped
  //matching actuallyIsDeclaration(DLanguageVarFuncDeclaration, ...)
  //methods are not found in DPsiImplUtil

  //WARNING: getDeclarationType(...) is skipped
  //matching getDeclarationType(DLanguageVarFuncDeclaration, ...)
  //methods are not found in DPsiImplUtil

  boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place);

}
