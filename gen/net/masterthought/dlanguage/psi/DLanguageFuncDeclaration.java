// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageFuncDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

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

  List<DLanguageParameter> getArguments();

  List<DLanguageProtectionAttribute> getProtection();

  List<DLanguageTemplateParameter> getTemplateArguments();

}
