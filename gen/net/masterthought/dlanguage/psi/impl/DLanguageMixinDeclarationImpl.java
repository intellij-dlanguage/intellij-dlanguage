package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageMixinDeclaration;
import net.masterthought.dlanguage.psi.DLanguageMixinExpression;
import net.masterthought.dlanguage.psi.DLanguageTemplateMixinExpression;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.OP_SCOLON;


public class DLanguageMixinDeclarationImpl extends ASTWrapperPsiElement implements DLanguageMixinDeclaration {
    public DLanguageMixinDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitMixinDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageTemplateMixinExpression getTemplateMixinExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateMixinExpression.class);
    }

    @Nullable
    public DLanguageMixinExpression getMixinExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageMixinExpression.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(OP_SCOLON);
    }

}
