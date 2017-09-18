package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageTemplateParameterList;
import net.masterthought.dlanguage.psi.DLanguageTemplateParameters;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_PAR_LEFT;
import static net.masterthought.dlanguage.psi.DlangTypes.OP_PAR_RIGHT;


public class DLanguageTemplateParametersImpl extends ASTWrapperPsiElement implements DLanguageTemplateParameters {
    public DLanguageTemplateParametersImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateParameters(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageTemplateParameterList getTemplateParameterList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateParameterList.class);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(OP_PAR_RIGHT);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(OP_PAR_LEFT);
    }

}
