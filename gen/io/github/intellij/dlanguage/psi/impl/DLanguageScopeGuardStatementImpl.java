package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageScopeGuardStatement;
import io.github.intellij.dlanguage.psi.DLanguageStatementNoCaseNoDefault;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DlangTypes;
import io.github.intellij.dlanguage.psi.DlangIdentifier;
import io.github.intellij.dlanguage.psi.DLanguageScopeGuardStatement;
import io.github.intellij.dlanguage.psi.DLanguageStatementNoCaseNoDefault;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static io.github.intellij.dlanguage.psi.DlangTypes.*;


public class DLanguageScopeGuardStatementImpl extends ASTWrapperPsiElement implements DLanguageScopeGuardStatement {
    public DLanguageScopeGuardStatementImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitScopeGuardStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public PsiElement getKW_SCOPE() {
        return findChildByType(DlangTypes.KW_SCOPE);
    }

    @Nullable
    public DlangIdentifier getIdentifier() {
        return PsiTreeUtil.getChildOfType(this, DlangIdentifier.class);
    }

    @Nullable
    public DLanguageStatementNoCaseNoDefault getStatementNoCaseNoDefault() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStatementNoCaseNoDefault.class);
    }

    @Nullable
    public PsiElement getOP_PAR_LEFT() {
        return findChildByType(DlangTypes.OP_PAR_LEFT);
    }

    @Nullable
    public PsiElement getOP_PAR_RIGHT() {
        return findChildByType(DlangTypes.OP_PAR_RIGHT);
    }

}
