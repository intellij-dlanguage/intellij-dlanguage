package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.psi.interfaces.HasMembers;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import net.masterthought.dlanguage.stubs.DLanguageTemplateDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTemplateDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageTemplateDeclarationStub>, HasMembers<DLanguageTemplateDeclarationStub> {
    @Nullable
    PsiElement getKW_TEMPLATE();

    @Nullable
    DLanguageIdentifier getIdentifier();

    @Nullable
    DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    DLanguageConstraint getConstraint();

    @Nullable
    PsiElement getOP_BRACES_RIGHT();

    @Nullable
    PsiElement getOP_BRACES_LEFT();

    @NotNull
    List<DLanguageDeclaration> getDeclarations();

    @Nullable
    DLanguageEponymousTemplateDeclaration getEponymousTemplateDeclaration();

    @Override
    default boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                        @NotNull ResolveState state,
                                        PsiElement lastParent,
                                        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
