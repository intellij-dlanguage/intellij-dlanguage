// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageModuleDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import java.util.Set;

public interface DLanguageGlobalDeclaration extends DNamedElement, GlobalDeclarationContainer, HasVisibility, StubBasedPsiElement<DLanguageModuleDeclarationStub> {

  @Nullable
  DLanguageAttribute getAttribute();

  @NotNull
  DLanguageModuleFullyQualifiedName getModuleFullyQualifiedName();

  @NotNull
  PsiElement getKwModule();

  @NotNull
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

  DLanguageProtectionAttribute getProtection();

  List<DLanguageClassDeclaration> getClassDeclarations(boolean includeFromMixins);

  List<DLanguageTemplateDeclaration> getTemplateDeclarations(boolean includeFromMixins);

  List<DLanguageStructDeclaration> getStructDeclarations(boolean includeFromMixins);

  List<DLanguageFuncDeclaration> getFunctionDeclarations(boolean includeFromMixins);

  List<DLanguageVarDeclarations> getVarDeclarations(boolean includeFromMixins);

  List<DLanguageVarDeclarations> getTopLevelVarDeclarations(boolean includeFromMixins);

  List<DLanguageFuncDeclaration> getTopLevelFunctionDeclarations(boolean includeFromMixins);

  List<DLanguageStructDeclaration> getTopLevelStructDeclarations(boolean includeFromMixins);

  List<DLanguageTemplateDeclaration> getTopLevelTemplateDeclarations(boolean includeFromMixins);

  List<DLanguageClassDeclaration> getTopLevelClassDeclarations(boolean includeFromMixins);

  Set<DNamedElement> getPubliclyAccessibleSymbols(boolean includeFromMixins);

  Set<DNamedElement> getAllSymbols(boolean includeFromMixins);

}
