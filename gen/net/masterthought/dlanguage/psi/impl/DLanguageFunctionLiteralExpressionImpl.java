package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.masterthought.dlanguage.psi.DLanguageTypes.KW_DELEGATE;
import static net.masterthought.dlanguage.psi.DLanguageTypes.KW_FUNCTION;


public class DLanguageFunctionLiteralExpressionImpl extends ASTWrapperPsiElement implements DLanguageFunctionLiteralExpression {
    public DLanguageFunctionLiteralExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitFunctionLiteralExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public PsiElement getKW_FUNCTION() {
        return findChildByType(KW_FUNCTION);
    }

    @Nullable
    public PsiElement getKW_DELEGATE() {
        return findChildByType(KW_DELEGATE);
    }

    @Nullable
    public DLanguageParameters getParameters() {
        return PsiTreeUtil.getChildOfType(this, DLanguageParameters.class);
    }

    @NotNull
    public List<DLanguageFunctionAttribute> getFunctionAttributes() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageFunctionAttribute.class);
    }

    @Nullable
    public DLanguageFunctionBody getFunctionBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFunctionBody.class);
    }
}
