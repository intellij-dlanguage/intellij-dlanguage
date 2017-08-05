package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasMembers;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import net.masterthought.dlanguage.stubs.DLanguageUnionDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageUnionDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageUnionDeclarationStub>, HasMembers<DLanguageUnionDeclarationStub> {
    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageConstraint getConstraint();

    @Nullable
    DLanguageStructBody getStructBody();

    @Nullable
    PsiElement getOP_SCOLON();

    @Override
    default boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        PsiElement lastParent,
                                        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
