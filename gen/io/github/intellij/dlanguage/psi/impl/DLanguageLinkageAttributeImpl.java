package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_EXTERN;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COMMA;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_RIGHT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PLUS_PLUS;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageIdentifierChain;
import io.github.intellij.dlanguage.psi.DLanguageLinkageAttribute;
import io.github.intellij.dlanguage.psi.named.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageLinkageAttributeImpl extends ASTWrapperPsiElement implements
    DLanguageLinkageAttribute {

    public DLanguageLinkageAttributeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitLinkageAttribute(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageIdentifierChain getIdentifierChain() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierChain.class);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PLUS_PLUS() {
        return findChildByType(OP_PLUS_PLUS);
    }

    @Nullable
    public PsiElement getKW_EXTERN() {
        return findChildByType(KW_EXTERN);
    }

    @Nullable
    public PsiElement getOP_COMMA() {
        return findChildByType(OP_COMMA);
    }

}
