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

public class DLanguageInvariantImpl extends ASTWrapperPsiElement implements DLanguageInvariant {

  public DLanguageInvariantImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitInvariant(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageBlockStatement getBlockStatement() {
    return findNotNullChildByClass(DLanguageBlockStatement.class);
  }

  @Override
  @Nullable
  public PsiElement getLParen() {
    return findChildByType(LPAREN);
  }

  @Override
  @Nullable
  public PsiElement getRParen() {
    return findChildByType(RPAREN);
  }

  @Override
  @NotNull
  public PsiElement getTInvariant() {
    return findNotNullChildByType(TINVARIANT);
  }

}
