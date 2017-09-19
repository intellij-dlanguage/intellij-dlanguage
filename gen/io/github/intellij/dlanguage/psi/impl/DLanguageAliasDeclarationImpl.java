package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageAliasDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAliasDeclaration {
    public DLanguageAliasDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAliasDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageIdentifierList getIdentifierList() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIdentifierList.class);
    }

    @Nullable
    public PsiElement getOP_COMMA() {
        return findChildByType(DlangTypes.OP_COMMA);
    }

    @NotNull
    public List<DLanguageStorageClass> getStorageClasss() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageStorageClass.class);
    }

    @Nullable
    public PsiElement getKW_ALIAS() {
        return findChildByType(DlangTypes.KW_ALIAS);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @Nullable
    public PsiElement getOP_SCOLON() {
        return findChildByType(DlangTypes.OP_SCOLON);
    }

    @NotNull
    public List<DLanguageAliasInitializer> getAliasInitializers() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageAliasInitializer.class);
    }
}
