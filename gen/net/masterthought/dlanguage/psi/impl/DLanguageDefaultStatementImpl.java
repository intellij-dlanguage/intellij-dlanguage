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

public class DLanguageDefaultStatementImpl extends ASTWrapperPsiElement implements DLanguageDefaultStatement {

  public DLanguageDefaultStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitDefaultStatement(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageScopeStatementList getScopeStatementList() {
    return findNotNullChildByClass(DLanguageScopeStatementList.class);
  }

  @Override
  @NotNull
  public PsiElement getKwDefault() {
    return findNotNullChildByType(KW_DEFAULT);
  }

  @Override
  @NotNull
  public PsiElement getOpColon() {
    return findNotNullChildByType(OP_COLON);
  }

}
