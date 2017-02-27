// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import net.masterthought.dlanguage.stubs.DLanguageAliasDeclarationStub;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface DLanguageAliasDeclaration extends MixinContainer, StubBasedPsiElement<DLanguageAliasDeclarationStub> {

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

}
