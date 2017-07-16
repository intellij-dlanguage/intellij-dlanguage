package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageTemplateThisParameter;
import net.masterthought.dlanguage.psi.DLanguageTemplateTypeParameter;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.KW_THIS;


public class DLanguageTemplateThisParameterImpl extends ASTWrapperPsiElement implements DLanguageTemplateThisParameter {
    public DLanguageTemplateThisParameterImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitTemplateThisParameter(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_THIS() {
        return findChildByType(KW_THIS);
    }

    @Nullable
    public DLanguageTemplateTypeParameter getTemplateTypeParameter() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateTypeParameter.class);
    }
}
