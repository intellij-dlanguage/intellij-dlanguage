package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAssignExpression;
import net.masterthought.dlanguage.psi.DLanguageTemplateArgument;
import net.masterthought.dlanguage.psi.DLanguageType;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageTemplateArgumentImpl extends ASTWrapperPsiElement implements DLanguageTemplateArgument {
    public DLanguageTemplateArgumentImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitTemplateArgument(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
    }
}
