// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageAliasDeclarationXImpl extends ASTWrapperPsiElement implements DLanguageAliasDeclarationX {

  public DLanguageAliasDeclarationXImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAliasDeclarationX(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAliasDeclarationX getAliasDeclarationX() {
    return findChildByClass(DLanguageAliasDeclarationX.class);
  }

  @Override
  @NotNull
  public DLanguageIdentifier getIdentifier() {
    return findNotNullChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageInitializer getInitializer() {
    return findChildByClass(DLanguageInitializer.class);
  }

  @Override
  @Nullable
  public DLanguageStorageClasses getStorageClasses() {
    return findChildByClass(DLanguageStorageClasses.class);
  }

  @Override
  @Nullable
  public DLanguageTemplateParameters getTemplateParameters() {
    return findChildByClass(DLanguageTemplateParameters.class);
  }

  @Override
  @Nullable
  public DLanguageType getType() {
    return findChildByClass(DLanguageType.class);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

  @Override
  @NotNull
  public PsiElement getOpEq() {
    return findNotNullChildByType(OP_EQ);
  }

}
