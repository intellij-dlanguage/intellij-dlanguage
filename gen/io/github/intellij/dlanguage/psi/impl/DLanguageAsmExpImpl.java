package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COLON;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_QUEST;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmExp;
import io.github.intellij.dlanguage.psi.DLanguageAsmLogOrExp;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageAsmExpImpl extends ASTWrapperPsiElement implements DLanguageAsmExp {

    public DLanguageAsmExpImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAsmExp(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageAsmLogOrExp getAsmLogOrExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmLogOrExp.class);
    }

    @Nullable
    public DLanguageAsmExp getAsmExp() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmExp.class);
    }

    @Nullable
    public PsiElement getOP_QUEST() {
        return findChildByType(OP_QUEST);
    }

    @Nullable
    public PsiElement getOP_COLON() {
        return findChildByType(OP_COLON);
    }

}
