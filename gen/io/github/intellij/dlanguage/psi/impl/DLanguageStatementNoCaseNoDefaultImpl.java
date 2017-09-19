package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.*;
import io.github.intellij.dlanguage.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageStatementNoCaseNoDefaultImpl extends ASTWrapperPsiElement implements DLanguageStatementNoCaseNoDefault {
    public DLanguageStatementNoCaseNoDefaultImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStatementNoCaseNoDefault(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) accept((DlangVisitor) visitor);
        else super.accept(visitor);
    }

    @Nullable
    public DLanguageLabeledStatement getLabeledStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageLabeledStatement.class);
    }

    @Nullable
    public DLanguageBlockStatement getBlockStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageBlockStatement.class);
    }

    @Nullable
    public DLanguageIfStatement getIfStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageIfStatement.class);
    }

    @Nullable
    public DLanguageWhileStatement getWhileStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageWhileStatement.class);
    }

    @Nullable
    public DLanguageDoStatement getDoStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDoStatement.class);
    }

    @Nullable
    public DLanguageForStatement getForStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageForStatement.class);
    }

    @Nullable
    public DLanguageForeachStatement getForeachStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageForeachStatement.class);
    }

    @Nullable
    public DLanguageSwitchStatement getSwitchStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageSwitchStatement.class);
    }

    @Nullable
    public DLanguageFinalSwitchStatement getFinalSwitchStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageFinalSwitchStatement.class);
    }

    @Nullable
    public DLanguageContinueStatement getContinueStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageContinueStatement.class);
    }

    @Nullable
    public DLanguageBreakStatement getBreakStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageBreakStatement.class);
    }

    @Nullable
    public DLanguageReturnStatement getReturnStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageReturnStatement.class);
    }

    @Nullable
    public DLanguageGotoStatement getGotoStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageGotoStatement.class);
    }

    @Nullable
    public DLanguageWithStatement getWithStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageWithStatement.class);
    }

    @Nullable
    public DLanguageSynchronizedStatement getSynchronizedStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageSynchronizedStatement.class);
    }

    @Nullable
    public DLanguageTryStatement getTryStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageTryStatement.class);
    }

    @Nullable
    public DLanguageThrowStatement getThrowStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageThrowStatement.class);
    }

    @Nullable
    public DLanguageScopeGuardStatement getScopeGuardStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageScopeGuardStatement.class);
    }

    @Nullable
    public DLanguageAsmStatement getAsmStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageAsmStatement.class);
    }

    @Nullable
    public DLanguageDebugSpecification getDebugSpecification() {
        return PsiTreeUtil.getChildOfType(this, DLanguageDebugSpecification.class);
    }

    @Nullable
    public DLanguageConditionalStatement getConditionalStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageConditionalStatement.class);
    }

    @Nullable
    public DLanguageVersionSpecification getVersionSpecification() {
        return PsiTreeUtil.getChildOfType(this, DLanguageVersionSpecification.class);
    }

    @Nullable
    public DLanguageStaticAssertStatement getStaticAssertStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageStaticAssertStatement.class);
    }

    @Nullable
    public DLanguageExpressionStatement getExpressionStatement() {
        return PsiTreeUtil.getChildOfType(this, DLanguageExpressionStatement.class);
    }
}
