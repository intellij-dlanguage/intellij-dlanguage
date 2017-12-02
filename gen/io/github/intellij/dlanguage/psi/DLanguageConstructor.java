package io.github.intellij.dlanguage.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveState;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.scope.PsiScopeProcessor;
import io.github.intellij.dlanguage.psi.interfaces.DNamedElement;
import io.github.intellij.dlanguage.resolve.ScopeProcessorImpl;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface DLanguageConstructor extends PsiElement, DNamedElement,
    StubBasedPsiElement<DLanguageConstructorStub> {

    @Nullable
    public DLanguageFunctionBody getFunctionBody();

    @Nullable
    public PsiElement getOP_SCOLON();

    @Nullable
    public PsiElement getOP_PAR_RIGHT();

    @Nullable
    public PsiElement getOP_PAR_LEFT();

    @Nullable
    public PsiElement getKW_THIS();

    @NotNull
    public List<DLanguageMemberFunctionAttribute> getMemberFunctionAttributes();

    @Nullable
    public DLanguageParameters getParameters();

    @Nullable
    public DLanguageTemplateParameters getTemplateParameters();

    @Override
    default public boolean processDeclarations(@NotNull PsiScopeProcessor processor,
        @NotNull ResolveState state,
        PsiElement lastParent,
        @NotNull PsiElement place) {
        return ScopeProcessorImpl.INSTANCE
            .processDeclarations(this, processor, state, lastParent, place);
    }
}
