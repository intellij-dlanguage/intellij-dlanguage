// This is a generated file. Not intended for manual editing.
package net.masterthought.dlanguage.psi.impl;

import net.masterthought.dlanguage.psi.interfaces.DLanguageIdentifier;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.masterthought.dlanguage.psi.*;

public class DLanguageAsmInstructionImpl extends ASTWrapperPsiElement implements DLanguageAsmInstruction {

  public DLanguageAsmInstructionImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof DLanguageVisitor) ((DLanguageVisitor)visitor).visitAsmInstruction(this);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public DLanguageAsmInstruction getAsmInstruction() {
    return findChildByClass(DLanguageAsmInstruction.class);
  }

  @Override
  @Nullable
  public DLanguageIdentifier getIdentifier() {
    return findChildByClass(DLanguageIdentifier.class);
  }

  @Override
  @Nullable
  public DLanguageIntegerExpression getIntegerExpression() {
    return findChildByClass(DLanguageIntegerExpression.class);
  }

  @Override
  @Nullable
  public DLanguageOpcode getOpcode() {
    return findChildByClass(DLanguageOpcode.class);
  }

  @Override
  @Nullable
  public DLanguageOperands getOperands() {
    return findChildByClass(DLanguageOperands.class);
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
