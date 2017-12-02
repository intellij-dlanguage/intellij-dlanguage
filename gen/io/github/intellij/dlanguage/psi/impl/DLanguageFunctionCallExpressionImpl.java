package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageArguments;
import io.github.intellij.dlanguage.psi.DLanguageFunctionCallExpression;
import io.github.intellij.dlanguage.psi.DLanguageTemplateArguments;
import io.github.intellij.dlanguage.psi.DLanguageType;
import io.github.intellij.dlanguage.psi.DLanguageUnaryExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageFunctionCallExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageFunctionCallExpression {

    public DLanguageFunctionCallExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitFunctionCallExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public DLanguageArguments getArguments() {
        return PsiTreeUtil.getChildOfType(this, DLanguageArguments.class);
    }

    @Nullable
    public DLanguageUnaryExpression getUnaryExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageUnaryExpression.class);
    }

    @Nullable
    public DLanguageTemplateArguments getTemplateArguments() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTemplateArguments.class);
    }
}
