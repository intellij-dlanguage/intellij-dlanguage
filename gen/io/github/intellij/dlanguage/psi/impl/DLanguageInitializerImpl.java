package io.github.intellij.dlanguage.psi.impl;

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_VOID;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageInitializer;
import io.github.intellij.dlanguage.psi.DLanguageNonVoidInitializer;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageInitializerImpl extends ASTWrapperPsiElement implements DLanguageInitializer {

    public DLanguageInitializerImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitInitializer(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public PsiElement getKW_VOID() {
        return findChildByType(KW_VOID);
    }

    @Nullable
    public DLanguageNonVoidInitializer getNonVoidInitializer() {
        return PsiTreeUtil.getChildOfType(this, DLanguageNonVoidInitializer.class);
    }
}
