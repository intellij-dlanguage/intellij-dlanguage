// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageAliasDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;
import net.masterthought.dlanguage.psi.interfaces.HasVisibility.Visibility;
import net.masterthought.dlanguage.psi.interfaces.Type;

public interface DLanguageAliasDeclaration extends DNamedElement, HasVisibility, StubBasedPsiElement<DLanguageAliasDeclarationStub> {

  @Nullable
  DLanguageAliasDeclarationX getAliasDeclarationX();

  @Nullable
  DLanguageBasicType getBasicType();

  @Nullable
  DLanguageBasicType2 getBasicType2();

  @Nullable
  DLanguageDeclarator getDeclarator();

  @Nullable
  DLanguageFuncDeclaratorSuffix getFuncDeclaratorSuffix();

  @Nullable
  DLanguageIdentifier getIdentifier();

  @Nullable
  DLanguageInitializer getInitializer();

  @Nullable
  DLanguageStorageClasses getStorageClasses();

  @Nullable
  DLanguageTemplateArguments getTemplateArguments();

  @Nullable
  DLanguageType getType();

  @NotNull
  PsiElement getKwAlias();

  @Nullable
  PsiElement getOpEq();

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

  boolean isSomeVisibility(Visibility visibility);

  boolean actuallyIsDeclaration();

  Type getDeclarationType();

}
