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
  public List<DLanguageIdentifier> getIdentifierList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public List<DLanguageStorageClasses> getStorageClassesList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageStorageClasses.class);
  }

  @Override
  @NotNull
  public List<DLanguageTemplateParameters> getTemplateParametersList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTemplateParameters.class);
  }

  @Override
  @NotNull
  public List<DLanguageType> getTypeList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageType.class);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

}
