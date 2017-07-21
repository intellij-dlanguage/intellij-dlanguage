package net.masterthought.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import net.masterthought.dlanguage.psi.interfaces.DNamedElement;
import net.masterthought.dlanguage.resolve.ScopeProcessorImpl;
import net.masterthought.dlanguage.stubs.DLanguageTemplateDeclarationStub;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public interface DLanguageTemplateDeclaration extends PsiElement, DNamedElement, StubBasedPsiElement<DLanguageTemplateDeclarationStub> {
    @Nullable
    public PsiElement getKW_TEMPLATE();

    @Nullable
    public DLanguageIdentifier getIdentifier();

    @Nullable
    public DLanguageTemplateParameters getTemplateParameters();

    @Nullable
    public DLanguageConstraint getConstraint();

    @Nullable
    public PsiElement getOP_BRACES_RIGHT();

    @Nullable
    public PsiElement getOP_BRACES_LEFT();

    @NotNull
    public List<DLanguageDeclaration> getDeclarations();

    @Nullable
    public DLanguageEponymousTemplateDeclaration getEponymousTemplateDeclaration();

    @Override
    default public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
                                               @NotNull ResolveState state,
                                               PsiElement lastParent,
                                               @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE.processDeclarations(this, processor, state, lastParent, place);
    }
}
