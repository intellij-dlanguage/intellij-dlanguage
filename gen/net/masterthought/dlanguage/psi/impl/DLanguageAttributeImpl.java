package net.masterthought.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import net.masterthought.dlanguage.psi.DLanguageAttribute;
import net.masterthought.dlanguage.psi.DLanguagePragmaExpression;
import net.masterthought.dlanguage.psi.DLanguageStorageClass;
import net.masterthought.dlanguage.psi.DLanguageVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.masterthought.dlanguage.psi.DLanguageTypes.*;


public class DLanguageAttributeImpl extends ASTWrapperPsiElement implements DLanguageAttribute {
    public DLanguageAttributeImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DLanguageVisitor visitor) {
        visitor.visitAttribute(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DLanguageVisitor) accept((DLanguageVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguagePragmaExpression getPragmaExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguagePragmaExpression.class);
    }

    @Nullable
    public DLanguageStorageClass getStorageClass() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStorageClass.class);
    }

    @Nullable
    public PsiElement getKW_EXPORT() {
        return findChildByType(KW_EXPORT);
    }

    @Nullable
    public PsiElement getKW_PACKAGE() {
        return findChildByType(KW_PACKAGE);
    }

    @Nullable
    public PsiElement getKW_PRIVATE() {
        return findChildByType(KW_PRIVATE);
    }

    @Nullable
    public PsiElement getKW_PROTECTED() {
        return findChildByType(KW_PROTECTED);
    }

    @Nullable
    public PsiElement getKW_PUBLIC() {
        return findChildByType(KW_PUBLIC);
    }

}
