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

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitConditionalDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageCondition getCondition() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageCondition.class));
  }

  @Override
  @Nullable
  public DLanguageDeclDefs getDeclDefs() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclDefs.class);
  }

  @Override
  @Nullable
  public DLanguageDeclarationBlock getDeclarationBlock() {
    return PsiTreeUtil.getChildOfType(this, DLanguageDeclarationBlock.class);
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
