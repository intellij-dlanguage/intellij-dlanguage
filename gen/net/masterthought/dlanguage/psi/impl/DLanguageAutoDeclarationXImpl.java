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

public class DLanguageAutoDeclarationXImpl extends ASTWrapperPsiElement implements DLanguageAutoDeclarationX {

  public DLanguageAutoDeclarationXImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAutoDeclarationX(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAutoDeclarationX getAutoDeclarationX() {
    return findChildByClass(DLanguageAutoDeclarationX.class);
  }

  @Override
  @NotNull
  public List<DLanguageIdentifier> getIdentifierList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageIdentifier.class);
  }

  @Override
  @NotNull
  public List<DLanguageInitializer> getInitializerList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageInitializer.class);
  }

  @Override
  @NotNull
  public List<DLanguageTemplateParameters> getTemplateParametersList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTemplateParameters.class);
  }

  @Override
  @Nullable
  public PsiElement getOpComma() {
    return findChildByType(OP_COMMA);
  }

}
