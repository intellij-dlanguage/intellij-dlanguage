package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DLanguageAsmInstruction;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DLanguageOperands;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageAsmInstructionImpl extends ASTWrapperPsiElement implements DLanguageAsmInstruction {
    public DLanguageAsmInstructionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmInstruction(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getINTEGER_LITERAL() {
        return findChildByType(DlangTypes.INTEGER_LITERAL);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public DLanguageAsmInstruction getAsmInstruction() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmInstruction.class);
    }

    @Nullable
    public DLanguageOperands getOperands() {
        return PsiTreeUtil.getChildOfType(this, DLanguageOperands.class);
    }

    @Nullable
    public PsiElement getKW_ALIGN() {
        return findChildByType(DlangTypes.KW_ALIGN);
    }

    @Nullable
    public PsiElement getOP_COLON() {
        return findChildByType(DlangTypes.OP_COLON);
    }

}
