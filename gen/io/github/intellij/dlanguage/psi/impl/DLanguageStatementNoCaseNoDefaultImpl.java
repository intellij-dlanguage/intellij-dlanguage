package io.github.intellij.dlanguage.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.intellij.dlanguage.psi.DLanguageAsmStatement;
import io.github.intellij.dlanguage.psi.DLanguageBlockStatement;
import io.github.intellij.dlanguage.psi.DLanguageBreakStatement;
import io.github.intellij.dlanguage.psi.DLanguageConditionalStatement;
import io.github.intellij.dlanguage.psi.DLanguageContinueStatement;
import io.github.intellij.dlanguage.psi.DLanguageDebugSpecification;
import io.github.intellij.dlanguage.psi.DLanguageDoStatement;
import io.github.intellij.dlanguage.psi.DLanguageExpressionStatement;
import io.github.intellij.dlanguage.psi.DLanguageFinalSwitchStatement;
import io.github.intellij.dlanguage.psi.DLanguageForStatement;
import io.github.intellij.dlanguage.psi.DLanguageForeachStatement;
import io.github.intellij.dlanguage.psi.DLanguageGotoStatement;
import io.github.intellij.dlanguage.psi.DLanguageIfStatement;
import io.github.intellij.dlanguage.psi.DLanguageReturnStatement;
import io.github.intellij.dlanguage.psi.DLanguageScopeGuardStatement;
import io.github.intellij.dlanguage.psi.DLanguageStatementNoCaseNoDefault;
import io.github.intellij.dlanguage.psi.DLanguageStaticAssertStatement;
import io.github.intellij.dlanguage.psi.DLanguageSwitchStatement;
import io.github.intellij.dlanguage.psi.DLanguageSynchronizedStatement;
import io.github.intellij.dlanguage.psi.DLanguageThrowStatement;
import io.github.intellij.dlanguage.psi.DLanguageTryStatement;
import io.github.intellij.dlanguage.psi.named.DLanguageVersionSpecification;
import io.github.intellij.dlanguage.psi.DLanguageWhileStatement;
import io.github.intellij.dlanguage.psi.DLanguageWithStatement;
import io.github.intellij.dlanguage.psi.named.DlangLabeledStatement;
import io.github.intellij.dlanguage.psi.DlangVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class DLanguageStatementNoCaseNoDefaultImpl extends ASTWrapperPsiElement implements
    DLanguageStatementNoCaseNoDefault {

    public DLanguageStatementNoCaseNoDefaultImpl(ASTNode node) {
        super(node);
    }

    public void accept(@NotNull DlangVisitor visitor) {
        visitor.visitStatementNoCaseNoDefault(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof DlangVisitor) {
            accept((DlangVisitor) visitor);
        } else {
            super.accept(visitor);
        }
    }

    @Nullable
    public DlangLabeledStatement getLabeledStatement() {
        return PsiTreeUtil.getChildOfType(this, DlangLabeledStatement.class);
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
