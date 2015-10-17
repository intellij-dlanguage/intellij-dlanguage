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

public class DLanguageReturnStatementImpl extends ASTWrapperPsiElement implements DLanguageReturnStatement {

  public DLanguageReturnStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitReturnStatement(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageExpression getExpression() {
    return findChildByClass(DLanguageExpression.class);
  }

  @Override
  @NotNull
  public PsiElement getKwReturn() {
    return findNotNullChildByType(KW_RETURN);
  }

  @Override
  @NotNull
  public PsiElement getOpScolon() {
    return findNotNullChildByType(OP_SCOLON);
  }

}
