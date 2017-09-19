package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageFunctionBody;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DLanguageFunctionBody;
import io.github.intellij.dlanguage.psi.DLanguageMemberFunctionAttribute;
import io.github.intellij.dlanguage.psi.DLanguagePostblit;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguagePostblitImpl extends ASTWrapperPsiElement implements DLanguagePostblit {
    public DLanguagePostblitImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitPostblit(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(DlangTypes.OP_SCOLON);
    }

    @NotNull
    public List<PsiElement> getKW_THISs() {
        return findChildrenByType(DlangTypes.KW_THIS);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(DlangTypes.OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(DlangTypes.OP_PAR_RIGHT);
    }

    @Nullable
    public DLanguageMemberFunctionAttribute getMemberFunctionAttribute() {
        return PsiTreeUtil.getChildOfType(this, DLanguageMemberFunctionAttribute.class);
    }
}
