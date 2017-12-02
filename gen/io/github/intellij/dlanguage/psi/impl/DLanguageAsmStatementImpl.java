package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_ASM;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_BRACES_RIGHT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmInstruction;
import io.github.intellij.dlanguage.psi.DLanguageAsmStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmStatementImpl extends ASTWrapperPsiElement implements
    DLanguageAsmStatement {

    public DLanguageAsmStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_ASM() {
        return findChildByType(KW_ASM);
    }

    @Nullable
    public PsiElement getOP_BRACES_LEFT() {
        return findChildByType(OP_BRACES_LEFT);
    }

    @Nullable
    public PsiElement getOP_BRACES_RIGHT() {
        return findChildByType(OP_BRACES_RIGHT);
    }

    @NotNull
    public List<DLanguageAsmInstruction> getAsmInstructions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAsmInstruction.class);
    }
}
