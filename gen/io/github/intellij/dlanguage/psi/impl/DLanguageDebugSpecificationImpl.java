package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageDebugSpecification;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DLanguageDebugSpecification;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageDebugSpecificationImpl extends ASTWrapperPsiElement implements DLanguageDebugSpecification {
    public DLanguageDebugSpecificationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitDebugSpecification(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_DEBUG() {
        return findChildByType(DlangTypes.KW_DEBUG);
    }

    @Nullable
    public PsiElement getOP_EQ() {
        return findChildByType(DlangTypes.OP_EQ);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public PsiElement getINTEGER_LITERAL() {
        return findChildByType(DlangTypes.INTEGER_LITERAL);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(DlangTypes.OP_SCOLON);
    }

}
