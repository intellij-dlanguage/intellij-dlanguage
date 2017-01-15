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

public class DLanguageAsmInstructionListImpl extends ASTWrapperPsiElement implements DLanguageAsmInstructionList {

  public DLanguageAsmInstructionListImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAsmInstructionList(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public DLanguageAsmInstruction getAsmInstruction() {
    return notNullChild(PsiTreeUtil.getChildOfType(this, DLanguageAsmInstruction.class));
  }

  @Override
  @Nullable
  public DLanguageAsmInstructionList getAsmInstructionList() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAsmInstructionList.class);
  }

  @Override
  @NotNull
  public PsiElement getOpScolon() {
    return notNullChild(findChildByType(OP_SCOLON));
  }

}
