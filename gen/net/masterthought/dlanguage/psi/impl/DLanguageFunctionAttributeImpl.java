package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAtAttribute;
import net.masterthought.dlanguage.psi.DLanguageFunctionAttribute;
import net.masterthought.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DlangTypes.KW_NOTHROW;
import static net.masterthought.dlanguage.psi.DlangTypes.KW_PURE;


public class DLanguageFunctionAttributeImpl extends ASTWrapperPsiElement implements DLanguageFunctionAttribute {
    public DLanguageFunctionAttributeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitFunctionAttribute(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAtAttribute getAtAttribute() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAtAttribute.class);
    }

    @Nullable
    public PsiElement getKW_PURE() {
        return findChildByType(KW_PURE);
    }

    @Nullable
    public PsiElement getKW_NOTHROW() {
        return findChildByType(KW_NOTHROW);
    }

}
