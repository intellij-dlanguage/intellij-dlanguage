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

public class DLanguageImportDeclarationImpl extends ASTWrapperPsiElement implements DLanguageImportDeclaration {

  public DLanguageImportDeclarationImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitImportDeclaration(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageImportList getImportList() {
    return findNotNullChildByClass(DLanguageImportList.class);
  }

  @Override
  @NotNull
  public PsiElement getKwImport() {
    return findNotNullChildByType(KW_IMPORT);
  }

  @Override
  @Nullable
  public PsiElement getKwStatic() {
    return findChildByType(KW_STATIC);
  }

  @Override
  @NotNull
  public PsiElement getOpScolon() {
    return findNotNullChildByType(OP_SCOLON);
  }

}
