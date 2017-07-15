
    package net.masterthought.dlanguage.psi;

    import com.intellij.psi.PsiElement;
    import org.jetbrains.annotations.NotNull;
    import static net.masterthought.dlanguage.psi.DLanguageTypes.*;
    import org.jetbrains.annotations.Nullable;
    import com.intellij.psi.util.PsiTreeUtil;
    import java.util.List;
    import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
    import net.masterthought.dlanguage.psi.interfaces.DCompositeElement;




    public interface DLanguageStatementNoCaseNoDefault extends PsiElement {
            @Nullable
            public DLanguageLabeledStatement getLabeledStatement();
            @Nullable
            public DLanguageBlockStatement getBlockStatement();
            @Nullable
            public DLanguageIfStatement getIfStatement();
            @Nullable
            public DLanguageWhileStatement getWhileStatement();
            @Nullable
            public DLanguageDoStatement getDoStatement();
            @Nullable
            public DLanguageForStatement getForStatement();
            @Nullable
            public DLanguageForeachStatement getForeachStatement();
            @Nullable
            public DLanguageSwitchStatement getSwitchStatement();
            @Nullable
            public DLanguageFinalSwitchStatement getFinalSwitchStatement();
            @Nullable
            public DLanguageContinueStatement getContinueStatement();
            @Nullable
            public DLanguageBreakStatement getBreakStatement();
            @Nullable
            public DLanguageReturnStatement getReturnStatement();
            @Nullable
            public DLanguageGotoStatement getGotoStatement();
            @Nullable
            public DLanguageWithStatement getWithStatement();
            @Nullable
            public DLanguageSynchronizedStatement getSynchronizedStatement();
            @Nullable
            public DLanguageTryStatement getTryStatement();
            @Nullable
            public DLanguageThrowStatement getThrowStatement();
            @Nullable
            public DLanguageScopeGuardStatement getScopeGuardStatement();
            @Nullable
            public DLanguageAsmStatement getAsmStatement();
            @Nullable
            public DLanguageDebugSpecification getDebugSpecification();
            @Nullable
            public DLanguageConditionalStatement getConditionalStatement();
            @Nullable
            public DLanguageVersionSpecification getVersionSpecification();
            @Nullable
            public DLanguageStaticAssertStatement getStaticAssertStatement();
            @Nullable
            public DLanguageExpressionStatement getExpressionStatement();
}