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

public class DLanguageAliasDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAliasDeclaration {

  public DLanguageAliasDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAliasDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAliasDeclarationSingle getAliasDeclarationSingle() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAliasDeclarationSingle.class);
  }

  @Override
  @Nullable
  public DLanguageAliasDeclarationX getAliasDeclarationX() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAliasDeclarationX.class);
  }

  @Override
  @Nullable
  public PsiElement getKwAlias() {
    return findChildByType(KW_ALIAS);
  }

  @Override
  @Nullable
  public PsiElement getOpScolon() {
    return findChildByType(OP_SCOLON);
  }

}
