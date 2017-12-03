

package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_SCOLON;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageMixinDeclaration;
import io.github.intellij.dlanguage.psi.DLanguageMixinExpression;
import io.github.intellij.dlanguage.psi.DLanguageTemplateMixinExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageMixinDeclarationImpl extends ASTWrapperPsiElement implements
    DLanguageMixinDeclaration {

    public DLanguageMixinDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitMixinDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
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
