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

public class DLanguageAttributeImpl extends ASTWrapperPsiElement implements DLanguageAttribute {

  public DLanguageAttributeImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAttribute(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAlignAttribute getAlignAttribute() {
    return findChildByClass(DLanguageAlignAttribute.class);
  }

  @Override
  @Nullable
  public DLanguageLinkageAttribute getLinkageAttribute() {
    return findChildByClass(DLanguageLinkageAttribute.class);
  }

  @Override
  @Nullable
  public DLanguagePragmaExpression getPragmaExpression() {
    return findChildByClass(DLanguagePragmaExpression.class);
  }

  @Override
  @Nullable
  public DLanguageStorageClass getStorageClass() {
    return findChildByClass(DLanguageStorageClass.class);
  }

  @Override
  @Nullable
  public PsiElement getExport() {
    return findChildByType(EXPORT);
  }

  @Override
  @Nullable
  public PsiElement getPackage() {
    return findChildByType(PACKAGE);
  }

  @Override
  @Nullable
  public PsiElement getPrivate() {
    return findChildByType(PRIVATE);
  }

  @Override
  @Nullable
  public PsiElement getProtected() {
    return findChildByType(PROTECTED);
  }

  @Override
  @Nullable
  public PsiElement getPublic() {
    return findChildByType(PUBLIC);
  }

}
