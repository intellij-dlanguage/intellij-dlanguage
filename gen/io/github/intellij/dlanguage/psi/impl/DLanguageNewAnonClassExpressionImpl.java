package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_CLASS;
import static io.github.intellij.dlanguage.psi.DlangTypes.KW_NEW;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageArguments;
import io.github.intellij.dlanguage.psi.DLanguageBaseClassList;
import io.github.intellij.dlanguage.psi.DLanguageNewAnonClassExpression;
import io.github.intellij.dlanguage.psi.DLanguageStructBody;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageNewAnonClassExpressionImpl extends ASTWrapperPsiElement implements
    DLanguageNewAnonClassExpression {

    public DLanguageNewAnonClassExpressionImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitNewAnonClassExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_NEW() {
        return findChildByType(KW_NEW);
    }

    @Nullable
    public PsiElement getKW_CLASS() {
        return findChildByType(KW_CLASS);
    }

    @NotNull
    public List<DLanguageArguments> getArgumentss() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageArguments.class);
    }

    @Nullable
    public DLanguageBaseClassList getBaseClassList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageBaseClassList.class);
    }

    @Nullable
    public DLanguageStructBody getStructBody() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStructBody.class);
    }
}
