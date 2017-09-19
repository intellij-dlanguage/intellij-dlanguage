package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.HasMembers;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangStructDeclarationStub;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.HasMembers;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangStructDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DlangStructDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DlangStructDeclarationStub>, HasMembers<DlangStructDeclarationStub> {
    @Nullable
    PsiElement getKW_STRUCT();

    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageConstraint getConstraint();

    @Nullable
    DLanguageStructBody getStructBody();

    @Nullable
    PsiElement getOP_SCOLON();

    @Override
    default boolean processDeclarations(@NotNull final PsiScopeProcessor processor,
                                        @NotNull final ResolveState state,
                                        final PsiElement lastParent,
                                        @NotNull final PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
