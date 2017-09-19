package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import io.github.intellij.dlanguage.psi.interfaces.HasMembers;
import io.github.intellij.dlanguage.stubs.DlangInterfaceOrClassStub;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.psi.interfaces.HasMembers;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import io.github.intellij.dlanguage.stubs.DlangInterfaceOrClassStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DlangInterfaceOrClass extends PsiElement, DNamedElement, StubBasedPsiElement<DlangInterfaceOrClassStub>, HasMembers<DlangInterfaceOrClassStub> {
    @Nullable
    DlangIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    PsiElement getOP_COLON();

    @Nullable
    DLanguageStructBody getStructBody();

    @Nullable
    DLanguageConstraint getConstraint();

    @Nullable
    DLanguageBaseClassList getBaseClassList();

    @Override
    default boolean processDeclarations(@NotNull final PsiScopeProcessor processor,
                                        @NotNull final ResolveState state,
                                        final PsiElement lastParent,
                                        @NotNull final PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
