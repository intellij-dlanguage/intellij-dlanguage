package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageNamespaceList;
import io.github.intellij.dlanguage.psi.DLanguageTernaryExpression;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COMMA;

public class DLanguageNamespaceListImpl extends ASTWrapperPsiElement implements
    DLanguageNamespaceList {

    public DLanguageNamespaceListImpl(ASTNode node) {
        super(node);
    }
    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitNamespaceList(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getOP_COMMA() {
        return findChildByType(OP_COMMA);
    }

    @Nullable
    public List<DLanguageTernaryExpression> getTernaryExpressions() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageTernaryExpression.class);
    }
}
