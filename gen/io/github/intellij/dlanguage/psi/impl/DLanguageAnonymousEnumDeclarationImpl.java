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

import static io.github.intellij.dlanguage.psi.DlangTypes.KW_ENUM;
import static io.github.intellij.dlanguage.psi.DlangTypes.OP_COLON;


public class DLanguageAnonymousEnumDeclarationImpl extends ASTWrapperPsiElement implements DLanguageAnonymousEnumDeclaration {
    public DLanguageAnonymousEnumDeclarationImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitAnonymousEnumDeclaration(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageAssignExpression getAssignExpression() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAssignExpression.class);
    }

    @Nullable
    public PsiElement getOP_COLON() {
        return findChildByType(DlangTypes.OP_COLON);
    }

    @Nullable
    public PsiElement getKW_ENUM() {
        return findChildByType(DlangTypes.KW_ENUM);
    }

    @Nullable
    public DLanguageType getType() {
        return PsiTreeUtil.getChildOfType(this, DLanguageType.class);
    }

    @NotNull
    public List<DLanguageEnumMember> getEnumMembers() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, DLanguageEnumMember.class);
    }
}
