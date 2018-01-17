package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import io.github.intellij.dlanguage.psi.named.DLanguageVersionSpecification;
import io.github.intellij.dlanguage.psi.named.DlangLabeledStatement;
import org.jetbrains.annotations.Nullable;


public interface DLanguageStatementNoCaseNoDefault extends PsiElement {

    @Nullable
    DlangLabeledStatement getLabeledStatement();

    @Nullable
    DLanguageBlockStatement getBlockStatement();

    @Nullable
    DLanguageIfStatement getIfStatement();

    @Nullable
    DLanguageWhileStatement getWhileStatement();

    @Nullable
    DLanguageDoStatement getDoStatement();

    @Nullable
    DLanguageForStatement getForStatement();

    @Nullable
    DLanguageForeachStatement getForeachStatement();

    @Nullable
    DLanguageSwitchStatement getSwitchStatement();

    @Nullable
    DLanguageFinalSwitchStatement getFinalSwitchStatement();

    @Nullable
    DLanguageContinueStatement getContinueStatement();

    @Nullable
    DLanguageBreakStatement getBreakStatement();

    @Nullable
    DLanguageReturnStatement getReturnStatement();

    @Nullable
    DLanguageGotoStatement getGotoStatement();

    @Nullable
    DLanguageWithStatement getWithStatement();

    @Nullable
    DLanguageSynchronizedStatement getSynchronizedStatement();

    @Nullable
    DLanguageTryStatement getTryStatement();

    @Nullable
    DLanguageThrowStatement getThrowStatement();

    @Nullable
    DLanguageScopeGuardStatement getScopeGuardStatement();

    @Nullable
    DLanguageAsmStatement getAsmStatement();

    @Nullable
    DLanguageDebugSpecification getDebugSpecification();

    @Nullable
    DLanguageConditionalStatement getConditionalStatement();

    @Nullable
    DLanguageVersionSpecification getVersionSpecification();

    @Nullable
    DLanguageStaticAssertStatement getStaticAssertStatement();

    @Nullable
    DLanguageExpressionStatement getExpressionStatement();
}
