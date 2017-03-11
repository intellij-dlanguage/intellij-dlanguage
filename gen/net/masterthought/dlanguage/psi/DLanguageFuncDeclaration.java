// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.containers.StatementContainer;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import net.masterthought.dlanguage.psi.interfaces.HasProperty;
import net.masterthought.dlanguage.psi.interfaces.HasTemplateArguments;
import net.masterthought.dlanguage.psi.interfaces.HasArguments;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageFuncDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;

public interface DLanguageFuncDeclaration extends StatementContainer, DNamedElement, HasVisibility, HasProperty, HasTemplateArguments, HasArguments, StubBasedPsiElement<DLanguageFuncDeclarationStub> {

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageBasicType2 getBasicType2();

  @NotNull
  DLanguageFuncDeclaratorSuffix getFuncDeclaratorSuffix();

  @Nullable
  DLanguageFunctionBody getFunctionBody();

  @NotNull
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageStorageClasses getStorageClasses();

  @Nullable
  PsiElement getOpEq();

  @Nullable
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

  @NotNull
  List<DLanguageParameter> getArguments();

  boolean isSomeVisibility(Visibility visibility);

  List<DLanguageTemplateParameter> getTemplateArguments();

  @NotNull
  List<DLanguageProtectionAttribute> getProtection();

  boolean isSystem();

  boolean isNoGC();

  boolean isTrusted();

  boolean hasCustomProperty();

  boolean isSafe();

  DLanguageUserDefinedAttribute getCustomProperty();

  boolean isPropertyFunction();

}
