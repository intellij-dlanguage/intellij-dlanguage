package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DLanguageTemplateTypeParameter;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COLON;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_EQ;


public class DLanguageTemplateTypeParameterImpl extends ASTWrapperPsiElement implements DLanguageTemplateTypeParameter {
    public DLanguageTemplateTypeParameterImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateTypeParameter(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @NotNull
    public List<DLanguageType> getTypes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageType.class);
    }

    @Nullable
    public PsiElement getOP_COLON() {
        return findChildByType(DlangTypes.OP_COLON);
    }

    @Nullable
    public PsiElement getOP_EQ() {
        return findChildByType(DlangTypes.OP_EQ);
    }

}
