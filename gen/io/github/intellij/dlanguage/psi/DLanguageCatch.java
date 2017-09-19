package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangCatchStub;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangCatchStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageCatch extends PsiElement, DNamedElement, StubBasedPsiElement<DlangCatchStub> {
    @Nullable
    PsiElement getKW_CATCH();

    @Nullable
    PsiElement getOP_PAR_LEFT();

    @Nullable
    PsiElement getOP_PAR_RIGHT();

    @Nullable
    DLanguageType getType();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageDeclarationOrStatement getDeclarationOrStatement();

    @Override
    default boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        PsiElement lastParent,
                                        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
