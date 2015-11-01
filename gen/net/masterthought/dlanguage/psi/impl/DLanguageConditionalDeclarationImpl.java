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

public class DLanguageConditionalDeclarationImpl extends ASTWrapperPsiElement implements DLanguageConditionalDeclaration {

  public DLanguageConditionalDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitConditionalDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageCondition getCondition() {
    return findNotNullChildByClass(DLanguageCondition.class);
  }

  @Override
  @Nullable
  public DLanguageDeclDefs getDeclDefs() {
    return findChildByClass(DLanguageDeclDefs.class);
  }

  @Override
  @NotNull
  public List<DLanguageDeclarationBlock> getDeclarationBlockList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageDeclarationBlock.class);
  }

  @Override
  @Nullable
  public PsiElement getKwElse() {
    return findChildByType(KW_ELSE);
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

}
