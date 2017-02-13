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

public class DLanguageAsmInstructionImpl extends ASTWrapperPsiElement implements DLanguageAsmInstruction {

  public DLanguageAsmInstructionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull DLanguageVisitor visitor) {
    visitor.visitAsmInstruction(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAsmInstruction getAsmInstruction() {
    return PsiTreeUtil.getChildOfType(this, DLanguageAsmInstruction.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageIntegerExpression getIntegerExpression() {
    return PsiTreeUtil.getChildOfType(this, DLanguageIntegerExpression.class);
  }

  @Override
  @Nullable
  public DLanguageOpcode getOpcode() {
    return PsiTreeUtil.getChildOfType(this, DLanguageOpcode.class);
  }

  @Override
  @Nullable
  public DLanguageOperands getOperands() {
    return PsiTreeUtil.getChildOfType(this, DLanguageOperands.class);
  }

  @Override
  @Nullable
  public PsiElement getKwAlign() {
    return findChildByType(KW_ALIGN);
  }

  @Override
  @Nullable
  public PsiElement getOpColon() {
    return findChildByType(OP_COLON);
  }

}
