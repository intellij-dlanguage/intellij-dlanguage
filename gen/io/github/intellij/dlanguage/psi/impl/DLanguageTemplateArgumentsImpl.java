package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_NOT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_LEFT;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_PAR_RIGHT;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageTemplateArgumentList;
import io.github.intellij.dlanguage.psi.DLanguageTemplateArguments;
import io.github.intellij.dlanguage.psi.DLanguageTemplateSingleArgument;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageTemplateArgumentsImpl extends ASTWrapperPsiElement implements
    DLanguageTemplateArguments {

    public DLanguageTemplateArgumentsImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateArguments(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageTemplateArgumentList getTemplateArgumentList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateArgumentList.class);
    }

    @Nullable
    public DLanguageTemplateSingleArgument getTemplateSingleArgument() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateSingleArgument.class);
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
    public PsiElement getOP_NOT() {
        return findChildByType(OP_NOT);
    }

}
