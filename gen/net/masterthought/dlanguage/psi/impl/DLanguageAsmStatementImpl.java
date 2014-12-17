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

public class DLanguageAsmStatementImpl extends ASTWrapperPsiElement implements DLanguageAsmStatement {

  public DLanguageAsmStatementImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAsmStatement(this);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<DLanguageAsmInstruction> getAsmInstructionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAsmInstruction.class);
  }

  @Override
  @NotNull
  public PsiElement getAsm() {
    return findNotNullChildByType(ASM);
  }

  @Override
  @NotNull
  public PsiElement getLBrace() {
    return findNotNullChildByType(LBRACE);
  }

  @Override
  @NotNull
  public PsiElement getRBrace() {
    return findNotNullChildByType(RBRACE);
  }

}
